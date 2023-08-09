package com.temantani.project.service.domain;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.temantani.domain.dto.BasicResponse;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.entity.ShareHolder;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.mapper.ProjectDataMapper;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRqeustedEventPayload;
import com.temantani.project.service.domain.outbox.scheduler.closefundraisingrequested.CloseFundraisingRequestedOutboxHelper;
import com.temantani.project.service.domain.outbox.scheduler.fundraisingregistered.FundraisingRegisteredOutboxHelper;
import com.temantani.project.service.domain.ports.input.service.ProjectApplicationService;
import com.temantani.project.service.domain.ports.output.repository.LandRepository;
import com.temantani.project.service.domain.ports.output.repository.ManagerRepository;
import com.temantani.project.service.domain.ports.output.repository.ProfitDistributionRepository;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;
import com.temantani.project.service.domain.ports.output.repository.ReceiverRepository;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

@Service
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

  private final DomainService domainService;
  private final ProjectDataMapper mapper;
  private final ManagerRepository managerRepository;
  private final LandRepository landRepository;
  private final ProjectRepository projectRepository;
  private final ReceiverRepository receiverRepository;
  private final ProfitDistributionRepository profitDistributionRepository;
  private final FundraisingRegisteredOutboxHelper fundraisingRegisteredOutboxHelper;
  private final CloseFundraisingRequestedOutboxHelper closeFundraisingRequestedOutboxHelper;

  public ProjectApplicationServiceImpl(DomainService domainService, ProjectDataMapper mapper,
      ManagerRepository managerRepository, LandRepository landRepository, ProjectRepository projectRepository,
      ReceiverRepository receiverRepository, ProfitDistributionRepository profitDistributionRepository,
      FundraisingRegisteredOutboxHelper fundraisingRegisteredOutboxHelper,
      CloseFundraisingRequestedOutboxHelper closeFundraisingRequestedOutboxHelper) {
    this.domainService = domainService;
    this.mapper = mapper;
    this.managerRepository = managerRepository;
    this.landRepository = landRepository;
    this.projectRepository = projectRepository;
    this.receiverRepository = receiverRepository;
    this.profitDistributionRepository = profitDistributionRepository;
    this.fundraisingRegisteredOutboxHelper = fundraisingRegisteredOutboxHelper;
    this.closeFundraisingRequestedOutboxHelper = closeFundraisingRequestedOutboxHelper;
  }

  @Override
  @Transactional
  public CreateProjectResponse createProject(UserId managerId, CreateProjectRequest request) {
    findManagerByIdOrThrow(managerId);
    Land land = findLandByIdOrThrow(new LandId(request.getLandId()));
    Project project = mapper.createProjectRequestToProject(request);

    ProjectCreatedEvent event = domainService.validateAndInitiateProject(managerId, project, land);

    projectRepository.save(project);
    landRepository.save(land);

    fundraisingRegisteredOutboxHelper
        .createOutbox(mapper.projectCreatedEventToFundraisingRegisteredEventPayload(event));

    return mapper.projectToCreateProjectResponse(project);
  }

  @Override
  @Transactional
  public BaseProjectResponse conitnueProjectToHiring(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);
    Project project = findProjectByIdOrThrow(projectId);

    domainService.intiateToHiring(managerId, project);

    projectRepository.save(project);
    closeFundraisingRequestedOutboxHelper
        .createOutbox(new CloseFundraisingRqeustedEventPayload(project.getId().getValue()));

    return BaseProjectResponse.builder()
        .projectId(project.getId().getValue())
        .managerId(project.getManagerId().getValue())
        .landId(project.getLandId().getValue())
        .status(project.getStatus())
        .build();
  }

  @Override
  @Transactional
  public BaseProjectResponse executeProject(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);
    Project project = findProjectByIdOrThrow(projectId);

    domainService.executeProject(managerId, project);

    projectRepository.save(project);

    return BaseProjectResponse.builder()
        .projectId(project.getId().getValue())
        .managerId(project.getManagerId().getValue())
        .landId(project.getLandId().getValue())
        .status(project.getStatus())
        .build();
  }

  @Override
  @Transactional
  public BaseProjectResponse finishProject(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);

    Project project = findProjectByIdOrThrow(projectId);
    Land land = findLandByIdOrThrow(project.getLandId());

    domainService.finishProject(managerId, project, land);

    projectRepository.save(project);
    landRepository.save(land);

    return BaseProjectResponse.builder()
        .projectId(project.getId().getValue())
        .managerId(project.getManagerId().getValue())
        .landId(project.getLandId().getValue())
        .status(project.getStatus())
        .build();
  }

  @Override
  @Transactional
  public BasicResponse addProjectExpense(UserId managerId, ProjectId projectId, CreateExpenseRequest request) {
    findManagerByIdOrThrow(managerId);
    Project project = findProjectByIdOrThrow(projectId);
    Expense expense = mapper.createExpenseRequestToExpense(request);

    domainService.addProjectExpense(managerId, project, expense);

    projectRepository.save(project);

    return BasicResponse.builder().message("Expense added").build();
  }

  @Override
  @Transactional
  public TrackProfitDistributionResponse generateProfitDistribution(UserId managerId, ProjectId projectId) {
    Project project = findProjectByIdOrThrow(projectId);

    List<UserId> receiverIds = project.getShareHolders().stream().map(ShareHolder::getReceiverId).toList();
    List<Receiver> receivers = findReceiversByIdIn(receiverIds);
    Map<UserId, BankAccount> receiverBank = receivers.stream()
        .collect(Collectors.toMap(Receiver::getId, Receiver::getBankAccount));

    ProfitDistribution profitDistribution = domainService.generateProfitDistribution(managerId, project, receiverBank);

    profitDistribution = profitDistributionRepository.save(profitDistribution);

    return mapper.profitDistributionToTrackProfitDistributionResponse(profitDistribution);
  }

  @Override
  @Transactional
  public TrackProfitDistributionResponse completeProfitDistribution(UserId managerId,
      ProfitDistributionId profitDistributionId,
      Map<ProfitDistributionDetailId, String> transferProof) {
    ProfitDistribution profitDistribution = findProfitDistributionByIdOrThrow(profitDistributionId);
    Project project = findProjectByIdOrThrow(profitDistribution.getProjectId());

    domainService.completeProfitDistribution(managerId, project, profitDistribution, transferProof);

    profitDistribution = profitDistributionRepository.save(profitDistribution);
    project = projectRepository.save(project);

    return mapper.profitDistributionToTrackProfitDistributionResponse(profitDistribution);
  }

  private Manager findManagerByIdOrThrow(UserId managerId) {
    return managerRepository.findById(managerId)
        .orElseThrow(() -> new ProjectDomainException("Manager not found"));
  }

  private Land findLandByIdOrThrow(LandId landId) {
    return landRepository.findById(landId)
        .orElseThrow(() -> new ProjectDomainException("Land not found"));
  }

  private Project findProjectByIdOrThrow(ProjectId projectId) {
    return projectRepository.findById(projectId)
        .orElseThrow(() -> new ProjectDomainException("Project not found"));
  }

  private ProfitDistribution findProfitDistributionByIdOrThrow(ProfitDistributionId profitDistributionId) {
    return profitDistributionRepository.findById(profitDistributionId)
        .orElseThrow(
            () -> new ProjectDomainException("Profit distribution not found for: " + profitDistributionId.getValue()));
  }

  private List<Receiver> findReceiversByIdIn(List<UserId> receiverIds) {
    return receiverRepository.findByIdIn(receiverIds)
        .orElseThrow(() -> new ProjectDomainException("Receiver not found"));
  }

}
