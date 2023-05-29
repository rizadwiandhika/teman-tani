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
import com.temantani.project.service.domain.dto.profitdistribution.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.event.ProjectStatusUpdatedEvent;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.mapper.ProjectDataMapper;
import com.temantani.project.service.domain.outbox.scheduler.ProjectStatusUpdatedOutboxHelper;
import com.temantani.project.service.domain.ports.input.service.ProjectApplicationService;
import com.temantani.project.service.domain.ports.output.repository.LandRepository;
import com.temantani.project.service.domain.ports.output.repository.ManagerRepository;
import com.temantani.project.service.domain.ports.output.repository.ProfitDistributionRepository;
import com.temantani.project.service.domain.ports.output.repository.ProjectRepository;
import com.temantani.project.service.domain.ports.output.repository.ReceiverRepository;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;
import com.temantani.project.service.domain.valueobject.ProfitReceiver;

@Service
public class ProjectApplicationServiceImpl implements ProjectApplicationService {

  private final DomainService domainService;
  private final ProjectDataMapper mapper;
  private final ManagerRepository managerRepository;
  private final LandRepository landRepository;
  private final ProjectRepository projectRepository;
  private final ReceiverRepository receiverRepository;
  private final ProfitDistributionRepository profitDistributionRepository;
  private final ProjectStatusUpdatedOutboxHelper projectStatusUpdatedOutboxHelper;

  public ProjectApplicationServiceImpl(DomainService domainService, ProjectDataMapper mapper,
      ManagerRepository managerRepository, LandRepository landRepository, ProjectRepository projectRepository,
      ReceiverRepository receiverRepository, ProfitDistributionRepository profitDistributionRepository,
      ProjectStatusUpdatedOutboxHelper projectStatusUpdatedOutboxHelper) {
    this.domainService = domainService;
    this.mapper = mapper;
    this.managerRepository = managerRepository;
    this.landRepository = landRepository;
    this.projectRepository = projectRepository;
    this.receiverRepository = receiverRepository;
    this.profitDistributionRepository = profitDistributionRepository;
    this.projectStatusUpdatedOutboxHelper = projectStatusUpdatedOutboxHelper;
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
    projectStatusUpdatedOutboxHelper
        .createProjectStatusUpdatedOutbox(mapper.projectCreatedEventToProjectStatusUpdatedEventPayload(event));

    return mapper.projectToCreateProjectResponse(project);
  }

  @Override
  @Transactional
  public BaseProjectResponse conitnueProjectToHiring(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);
    Project project = findProjectByIdOrThrow(projectId);

    ProjectStatusUpdatedEvent event = domainService.continueProjectToHiring(managerId, project);

    // ? Should i improve this using Saga since investment service should close the
    // ? project first the we're good to go to hiring

    projectRepository.save(project);
    projectStatusUpdatedOutboxHelper
        .createProjectStatusUpdatedOutbox(mapper.projectStatusUpdatedEventToProjectStatusUpdatedEventPayload(event));

    return mapper.projectStatusUpdatedEventToBaseProjectResponse(event);
  }

  @Override
  @Transactional
  public BaseProjectResponse executeProject(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);
    Project project = findProjectByIdOrThrow(projectId);

    ProjectStatusUpdatedEvent event = domainService.executeProject(managerId, project);

    projectRepository.save(project);
    projectStatusUpdatedOutboxHelper
        .createProjectStatusUpdatedOutbox(mapper.projectStatusUpdatedEventToProjectStatusUpdatedEventPayload(event));

    return mapper.projectStatusUpdatedEventToBaseProjectResponse(event);
  }

  @Override
  @Transactional
  public BaseProjectResponse finishProject(UserId managerId, ProjectId projectId) {
    findManagerByIdOrThrow(managerId);

    Project project = findProjectByIdOrThrow(projectId);
    Land land = findLandByIdOrThrow(project.getLandId());

    ProjectStatusUpdatedEvent event = domainService.finishProject(managerId, project, land);

    projectRepository.save(project);
    landRepository.save(land);
    projectStatusUpdatedOutboxHelper
        .createProjectStatusUpdatedOutbox(mapper.projectStatusUpdatedEventToProjectStatusUpdatedEventPayload(event));

    return mapper.projectStatusUpdatedEventToBaseProjectResponse(event);
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
  public TrackProfitDistributionResponse intiateProfitDistribution(UserId managerId, ProjectId projectId) {
    Project project = findProjectByIdOrThrow(projectId);

    List<UserId> receiverIds = project.getProfitReceivers().stream().map(ProfitReceiver::getReceiverId).toList();
    List<Receiver> receivers = findReceiversByIdIn(receiverIds);
    Map<UserId, BankAccount> receiverBank = receivers.stream()
        .collect(Collectors.toMap(Receiver::getId, Receiver::getBankAccount));

    ProfitDistribution profitDistribution = domainService.initiateProfitDistribution(managerId, project, receiverBank);

    profitDistribution = profitDistributionRepository.save(profitDistribution);

    return mapper.profitDistributionToTrackProfitDistributionResponse(profitDistribution);
  }

  @Override
  @Transactional
  public TrackProfitDistributionResponse completeProfitDistribution(UserId managerId,
      ProfitDistributionId profitDistributionId,
      Map<ProfitDistributionDetailId, String> transferProof) {
    ProfitDistribution profitDistribution = findProfitDistributionByIdOrThrow(profitDistributionId);

    domainService.completeProfitDistribution(managerId, profitDistribution, transferProof);

    profitDistribution = profitDistributionRepository.save(profitDistribution);

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
