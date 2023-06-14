package com.temantani.project.service.domain.mapper;

import java.math.RoundingMode;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.dto.query.ExpenseData;
import com.temantani.project.service.domain.dto.query.ProfitDistributionDetailDto;
import com.temantani.project.service.domain.dto.query.ProjectData;
import com.temantani.project.service.domain.dto.query.ProjectDataDetails;
import com.temantani.project.service.domain.dto.query.ShareHolderData;
import com.temantani.project.service.domain.dto.query.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.entity.ShareHolder;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.event.ProjectStatusUpdatedEvent;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredEventPayload;

@Component
public class ProjectDataMapper {

  public Project createProjectRequestToProject(CreateProjectRequest request) {
    return Project.builder()
        .landId(new LandId(request.getLandId()))
        .description(request.getDescription())
        .harvest(request.getHarvest())
        .workerNeeds(request.getWorkerNeeds())
        .fundraisingTarget(new Money(request.getFundaisingTarget()))
        .fundraisingDeadline(request.getFundaisingDeadline())
        .estimatedFinished(request.getEstimatedFinished())
        .build();
  }

  public CreateProjectResponse projectToCreateProjectResponse(Project project) {
    return CreateProjectResponse.builder()
        .projectId(project.getId().getValue())
        .managerId(project.getManagerId().getValue())
        .landId(project.getLandId().getValue())
        .status(project.getStatus())
        .description(project.getDescription())
        .harvest(project.getHarvest())
        .workerNeeds(project.getWorkerNeeds())
        .fundaisingTarget(project.getFundraisingTarget().getAmount())
        .fundaisingDeadline(project.getFundraisingDeadline())
        .estimatedFinished(project.getEstimatedFinished())
        .build();
  }

  public BaseProjectResponse projectStatusUpdatedEventToBaseProjectResponse(ProjectStatusUpdatedEvent event) {
    return BaseProjectResponse.builder()
        .projectId(event.getProject().getId().getValue())
        .managerId(event.getProject().getManagerId().getValue())
        .landId(event.getProject().getLandId().getValue())
        .status(event.getProject().getStatus())
        .build();
  }

  public Expense createExpenseRequestToExpense(CreateExpenseRequest request) {
    return Expense.builder()
        .name(request.getName())
        .description(request.getDescription())
        .amount(new Money(request.getAmount()))
        .invoiceUrl(request.getInvoiceUrl())
        .build();
  }

  public TrackProfitDistributionResponse profitDistributionToTrackProfitDistributionResponse(
      ProfitDistribution distribution) {
    return TrackProfitDistributionResponse.builder()
        .id(distribution.getId().getValue())
        .projectId(distribution.getProjectId().getValue())
        .distributedAt(distribution.getDistributedAt())
        .status(distribution.getStatus())
        .details(distribution.getDetails().stream().map((detail) -> ProfitDistributionDetailDto.builder()
            .id(detail.getId().getValue())
            .profitDistributionId(detail.getProfitDistributionId().getValue())
            .receiverId(detail.getReceiver().getReceiverId().getValue())
            .distributionType(detail.getReceiver().getType())
            .devidend(detail.getReceiver().getDevidend().setScale(2, RoundingMode.HALF_EVEN))
            .amount(detail.getAmount().getAmount())
            .transferProof(detail.getTransferProofUrl())
            .bankAccount(detail.getBankAccount())
            .build())
            .collect(Collectors.toList()))
        .build();
  }

  public Land createLandMessageToLand(LandRegisteredMessage message) {
    return Land.builder()
        .id(new LandId(message.getLandId()))
        .ownerId(new UserId(message.getOwnerId()))
        .address(message.getAddress())
        .landStatus(message.getLandStatus())
        .build();
  }

  public Manager createManagerMessageToManager(ManagerRegisteredMessage message) {
    return new Manager(new UserId(message.getManagerId()), message.getEmail(), message.getName());
  }

  public Receiver createReceiverMessageToReceiver(ReceiverRegisteredMessage message) {
    return new Receiver(new UserId(message.getReceiverId()), message.getBankAccount());
  }

  public FundraisingRegisteredEventPayload projectStatusUpdatedEventToProjectStatusUpdatedEventPayload(
      ProjectStatusUpdatedEvent event) {
    Project project = event.getProject();

    return FundraisingRegisteredEventPayload.builder()
        .projectId(project.getId().getValue().toString())
        .managerId(project.getManagerId().getValue().toString())
        .landId(project.getLandId().getValue().toString())
        .description(project.getDescription())
        .details(project.getDetails())
        .harvest(project.getHarvest())
        .status(project.getStatus())
        .workerNeeds(project.getWorkerNeeds())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .fundraisingDeadline(project.getFundraisingDeadline())
        .estimatedFinished(project.getEstimatedFinished())
        .createdAt(project.getCreatedAt())
        .executedAt(project.getExecutedAt())
        .finishedAt(project.getFinishedAt())
        .build();
  }

  public FundraisingRegisteredEventPayload projectCreatedEventToFundraisingRegisteredEventPayload(
      ProjectCreatedEvent event) {
    Project project = event.getProject();

    return FundraisingRegisteredEventPayload.builder()
        .projectId(project.getId().getValue().toString())
        .managerId(project.getManagerId().getValue().toString())
        .landId(project.getLandId().getValue().toString())
        .description(project.getDescription())
        .details(project.getDetails())
        .harvest(project.getHarvest())
        .status(project.getStatus())
        .workerNeeds(project.getWorkerNeeds())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .fundraisingDeadline(project.getFundraisingDeadline())
        .estimatedFinished(project.getEstimatedFinished())
        .createdAt(project.getCreatedAt())
        .executedAt(project.getExecutedAt())
        .finishedAt(project.getFinishedAt())
        .build();
  }

  public ProjectDataDetails projectToProjectDataDetails(Project project) {
    return ProjectDataDetails.builder()
        .id(project.getId().getValue().toString())
        .landId(project.getLandId().getValue().toString())
        .managerId(project.getManagerId().getValue().toString())
        .description(project.getDescription())
        .details(project.getDetails())
        .harvest(project.getHarvest())
        .status(project.getStatus())
        .workerNeeds(project.getWorkerNeeds())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .fundraisingDeadline(project.getFundraisingDeadline())
        .estimatedFinished(project.getEstimatedFinished())
        .collectedFunds(project.getCollectedFunds().getAmount())
        .income(project.getIncome().getAmount())
        .distributedIncome(project.getDistributedIncome().getAmount())
        .shareHolders(project.getShareHolders() == null
            ? null
            : project.getShareHolders().stream().map(this::shareHolderToShareHolderData)
                .collect(Collectors.toList()))
        .expenses(project.getExpenses() == null
            ? null
            : project.getExpenses().stream().map(this::expenseToExpenseData).collect(Collectors.toList()))
        .createdAt(project.getCreatedAt())
        .executedAt(project.getExecutedAt())
        .finishedAt(project.getFinishedAt())
        .failureMessages(project.getFailureMessages())
        .build();
  }

  public ProjectData projectToProjectData(Project project) {
    return ProjectData.builder()
        .id(project.getId().getValue().toString())
        .description(project.getDescription())
        .harvest(project.getHarvest())
        .status(project.getStatus())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .estimatedFinished(project.getEstimatedFinished())
        .collectedFunds(project.getCollectedFunds().getAmount())
        .createdAt(project.getCreatedAt())
        .executedAt(project.getExecutedAt())
        .finishedAt(project.getFinishedAt())
        .build();
  }

  public ShareHolderData shareHolderToShareHolderData(ShareHolder shareHolder) {
    return ShareHolderData.builder()
        .userId(shareHolder.getReceiverId().getValue().toString())
        .type(shareHolder.getType().name())
        .devidend(shareHolder.getDevidend())
        .build();
  }

  public ExpenseData expenseToExpenseData(Expense expense) {
    return ExpenseData.builder()
        .id(expense.getId().getValue().toString())
        .projectId(expense.getProjectId().getValue().toString())
        .name(expense.getName())
        .description(expense.getDescription())
        .invoiceUrl(expense.getInvoiceUrl())
        .amount(expense.getAmount().getAmount())
        .createdAt(expense.getCreatedAt())
        .build();
  }

}
