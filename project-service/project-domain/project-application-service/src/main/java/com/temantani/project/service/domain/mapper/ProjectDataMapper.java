package com.temantani.project.service.domain.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.domain.dto.project.CreateProjectRequest;
import com.temantani.project.service.domain.dto.project.CreateProjectResponse;
import com.temantani.project.service.domain.dto.message.land.LandRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ManagerRegisteredMessage;
import com.temantani.project.service.domain.dto.message.user.ReceiverRegisteredMessage;
import com.temantani.project.service.domain.dto.profitdistribution.ProfitDistributionDetailDto;
import com.temantani.project.service.domain.dto.profitdistribution.TrackProfitDistributionResponse;
import com.temantani.project.service.domain.dto.project.BaseProjectResponse;
import com.temantani.project.service.domain.dto.project.CreateExpenseRequest;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.event.ProjectCreatedEvent;
import com.temantani.project.service.domain.event.ProjectStatusUpdatedEvent;
import com.temantani.project.service.domain.outbox.model.ProjectStatusUpdatedEventPayload;

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
            .devidend(detail.getReceiver().getDevidend())
            .amount(detail.getAmount().getAmount())
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

  public ProjectStatusUpdatedEventPayload projectStatusUpdatedEventToProjectStatusUpdatedEventPayload(
      ProjectStatusUpdatedEvent event) {
    Project project = event.getProject();

    return ProjectStatusUpdatedEventPayload.builder()
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

  public ProjectStatusUpdatedEventPayload projectCreatedEventToProjectStatusUpdatedEventPayload(
      ProjectCreatedEvent event) {
    Project project = event.getProject();

    return ProjectStatusUpdatedEventPayload.builder()
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
}
