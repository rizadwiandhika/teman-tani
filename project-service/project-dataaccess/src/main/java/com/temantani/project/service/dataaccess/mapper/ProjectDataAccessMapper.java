package com.temantani.project.service.dataaccess.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.temantani.domain.valueobject.Address;
import com.temantani.domain.valueobject.BankAccount;
import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.LandId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.project.service.dataaccess.entity.ExpenseEntity;
import com.temantani.project.service.dataaccess.entity.InvestmentEntity;
import com.temantani.project.service.dataaccess.entity.LandEntity;
import com.temantani.project.service.dataaccess.entity.ManagerEntity;
import com.temantani.project.service.dataaccess.entity.OutboxEntity;
import com.temantani.project.service.dataaccess.entity.ProfitDistributionDetailEntity;
import com.temantani.project.service.dataaccess.entity.ProfitDistributionEntity;
import com.temantani.project.service.dataaccess.entity.ProfitReceiverEntity;
import com.temantani.project.service.dataaccess.entity.ProjectEntity;
import com.temantani.project.service.dataaccess.entity.ReceiverEntity;
import com.temantani.project.service.dataaccess.entity.type.OutboxType;
import com.temantani.project.service.domain.entity.Expense;
import com.temantani.project.service.domain.entity.Investment;
import com.temantani.project.service.domain.entity.Land;
import com.temantani.project.service.domain.entity.Manager;
import com.temantani.project.service.domain.entity.ProfitDistribution;
import com.temantani.project.service.domain.entity.ProfitDistributionDetail;
import com.temantani.project.service.domain.entity.Project;
import com.temantani.project.service.domain.entity.Receiver;
import com.temantani.project.service.domain.entity.ShareHolder;
import com.temantani.project.service.domain.exception.ProjectDomainException;
import com.temantani.project.service.domain.outbox.model.closefundraisingrequested.CloseFundraisingRequestedOutboxMessage;
import com.temantani.project.service.domain.outbox.model.fundraisingregistered.FundraisingRegisteredOutboxMessage;
import com.temantani.project.service.domain.valueobject.ExpenseId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionDetailId;
import com.temantani.project.service.domain.valueobject.ProfitDistributionId;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProjectDataAccessMapper {

  private final ObjectMapper objectMapper;

  public ProjectDataAccessMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public InvestmentEntity investmentToInvestmentEntity(Investment investment) {
    return InvestmentEntity.builder()
        .id(investment.getId().getValue())
        .projectId(investment.getProjectId().getValue())
        .investorId(investment.getInvestorId().getValue())
        .amount(investment.getAmount().getAmount())
        .build();
  }

  public Investment investmentEntityToInvestment(InvestmentEntity entity) {
    return Investment.builder()
        .id(new InvestmentId(entity.getId()))
        .projectId(new ProjectId(entity.getProjectId()))
        .investorId(new UserId(entity.getInvestorId()))
        .amount(new Money(entity.getAmount()))
        .build();
  }

  public Land landEntityToLand(LandEntity entity) {
    return Land.builder()
        .id(new LandId(entity.getId()))
        .version(entity.getVersion())
        .ownerId(new UserId(entity.getOwnerId()))
        .address(new Address(
            entity.getStreet(),
            entity.getCity(),
            entity.getPostalCode()))
        .landStatus(entity.getAvailabilityStatus())
        .build();
  }

  public LandEntity landToLandEntity(Land land) {
    return LandEntity.builder()
        .id(land.getId().getValue())
        .version(land.getVersion())
        .ownerId(land.getOwnerId().getValue())
        .street(land.getAddress().getStreet())
        .city(land.getAddress().getCity())
        .postalCode(land.getAddress().getPostalCode())
        .availabilityStatus(land.getAvailabilityStatus())
        .build();
  }

  public Manager managerEntityToManager(ManagerEntity entity) {
    return new Manager(new UserId(entity.getId()), entity.getEmail(), entity.getName());
  }

  public ManagerEntity managerToManagerEntity(Manager manager) {
    return ManagerEntity.builder()
        .id(manager.getId().getValue())
        .email(manager.getEmail())
        .name(manager.getName())
        .build();
  }

  public ReceiverEntity receiverToReceiverEntity(Receiver receiver) {
    return ReceiverEntity.builder()
        .id(receiver.getId().getValue())
        .version(receiver.getVersion())
        .bank(receiver.getBankAccount().getBank())
        .accountNumber(receiver.getBankAccount().getAccountNumber())
        .accountHolderName(receiver.getBankAccount().getAccountHolderName())
        .build();
  }

  public Receiver receiverEntityToReceiver(ReceiverEntity entity) {
    return new Receiver(new UserId(entity.getId()), entity.getVersion(),
        new BankAccount(entity.getBank(), entity.getAccountNumber(), entity.getAccountHolderName()));
  }

  public ProjectEntity projectToProjectEntity(Project project) {
    ProjectEntity entity = ProjectEntity.builder()
        .id(project.getId().getValue())
        .status(project.getStatus())
        .version(project.getVersion())
        .profitReceivers(project.getShareHolders() == null ? new ArrayList<>()
            : project.getShareHolders().stream().map(this::profitReceiverToProfitReceiverEntity)
                .collect(Collectors.toList()))
        .expenses(project.getExpenses() == null ? new ArrayList<>()
            : project.getExpenses().stream().map(this::expenseToExpenseEntity).collect(Collectors.toList()))
        .landId(project.getLandId().getValue())
        .managerId(project.getManagerId().getValue())
        .description(project.getDescription())
        .details(writeDetailAsJsonString(project.getDetails()))
        .harvest(project.getHarvest())
        .workerNeeds(project.getWorkerNeeds())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .fundraisingDeadline(project.getFundraisingDeadline())
        .estimatedFinished(project.getEstimatedFinished())
        .collectedFunds(project.getCollectedFunds().getAmount())
        .income(project.getIncome().getAmount())
        .distributedIncome(project.getDistributedIncome().getAmount())
        .createdAt(project.getCreatedAt())
        .executedAt(project.getExecutedAt())
        .finishedAt(project.getFinishedAt())
        .sellingIds(project.getSellingIds() == null || project.getSellingIds().size() == 0
            ? null
            : String.join(Project.FAILURE_MESSAGES_DELIMITER,
                project.getSellingIds().stream().map(UUID::toString).toList()))
        .failureMessages(project.getFailureMessages() == null || project.getFailureMessages().size() == 0
            ? null
            : String.join(Project.FAILURE_MESSAGES_DELIMITER, project.getFailureMessages()))
        .build();

    entity.getProfitReceivers().forEach((p) -> p.setProject(entity));
    entity.getExpenses().forEach((e) -> e.setProject(entity));

    return entity;
  }

  private String writeAsJson(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public ProfitReceiverEntity profitReceiverToProfitReceiverEntity(ShareHolder receiver) {
    return ProfitReceiverEntity.builder()
        .id(receiver.getId())
        .type(receiver.getType())
        .receiverId(receiver.getReceiverId().getValue())
        .devidend(receiver.getDevidend())
        .build();
  }

  public ExpenseEntity expenseToExpenseEntity(Expense expense) {
    return ExpenseEntity.builder()
        .id(expense.getId().getValue())
        .createdAt(expense.getCreatedAt())
        .name(expense.getName())
        .description(expense.getDescription())
        .invoiceUrl(expense.getInvoiceUrl())
        .amount(expense.getAmount().getAmount())
        .build();
  }

  public Project projectEntityToProject(ProjectEntity entity) {
    return Project.builder()
        .id(new ProjectId(entity.getId()))
        .status(entity.getStatus())
        .version(entity.getVersion())
        .profitReceivers(entity.getProfitReceivers() == null ? new ArrayList<>()
            : entity.getProfitReceivers().stream().map(this::profitReceiverEntityToProfitReceiver)
                .collect(Collectors.toList()))
        .expenses(entity.getExpenses() == null ? new ArrayList<>()
            : entity.getExpenses().stream().map(this::expenseEntityToExpense).collect(Collectors.toList()))
        .landId(new LandId(entity.getLandId()))
        .managerId(new UserId(entity.getManagerId()))
        .description(entity.getDescription())
        .details(readDetailFromJsonString(entity.getDetails()))
        .harvest(entity.getHarvest())
        .workerNeeds(entity.getWorkerNeeds())
        .fundraisingTarget(new Money(entity.getFundraisingTarget()))
        .fundraisingDeadline(entity.getFundraisingDeadline())
        .estimatedFinished(entity.getEstimatedFinished())
        .collectedFunds(new Money(entity.getCollectedFunds()))
        .income(new Money(entity.getIncome()))
        .distributedIncome(new Money(entity.getDistributedIncome()))
        .createdAt(entity.getCreatedAt())
        .executedAt(entity.getExecutedAt())
        .finishedAt(entity.getFinishedAt())
        .setSellingIds(getSellingIds(entity))
        .failureMessages(
            entity.getFailureMessages() == null || entity.getFailureMessages().trim().length() == 0
                ? new ArrayList<>()
                : new ArrayList<>(Arrays.asList(entity.getFailureMessages().split(Project.FAILURE_MESSAGES_DELIMITER))))
        .build();
  }

  private List<UUID> getSellingIds(ProjectEntity entity) {

    if (entity.getSellingIds() == null || entity.getSellingIds().trim().length() == 0) {
      return new ArrayList<>();
    }
    return new ArrayList<>(Arrays.asList(entity.getSellingIds().split(Project.FAILURE_MESSAGES_DELIMITER))
        .stream().map((s) -> {
          log.info("UUID string: {}", s);
          return UUID.fromString(s);
        }).collect(Collectors.toList()));
  }

  public ShareHolder profitReceiverEntityToProfitReceiver(ProfitReceiverEntity entity) {
    return ShareHolder.builder()
        .id(entity.getId())
        .deviden(entity.getDevidend())
        .receiverId(new UserId(entity.getReceiverId()))
        .type(entity.getType())
        .build();
  }

  public Expense expenseEntityToExpense(ExpenseEntity entity) {
    return Expense.builder()
        .id(new ExpenseId(entity.getId()))
        .projectId(new ProjectId(entity.getProject().getId()))
        .createdAt(entity.getCreatedAt())
        .name(entity.getName())
        .description(entity.getDescription())
        .invoiceUrl(entity.getInvoiceUrl())
        .amount(new Money(entity.getAmount()))
        .build();
  }

  public ProfitDistributionEntity profitDistributioToProfitDistributionEntity(ProfitDistribution distribution) {
    ProfitDistributionEntity entity = ProfitDistributionEntity.builder()
        .id(distribution.getId().getValue())
        .managerId(distribution.getManagerId().getValue())
        .details(distribution.getDetails() == null ? null
            : distribution.getDetails().stream().map(this::profitDistributionDetailToProfitDistributionDetailEntity)
                .collect(Collectors.toList()))
        .status(distribution.getStatus())
        .projectId(distribution.getProjectId().getValue())
        .projectProfit(distribution.getProjectProfit().getAmount())
        .distributedAt(distribution.getDistributedAt())
        .build();

    entity.getDetails().forEach(p -> p.setProfitDistribution(entity));

    return entity;
  }

  public ProfitDistributionDetailEntity profitDistributionDetailToProfitDistributionDetailEntity(
      ProfitDistributionDetail detail) {
    return ProfitDistributionDetailEntity.builder()
        .id(detail.getId().getValue())
        .type(detail.getReceiver().getType())
        .devidend(detail.getReceiver().getDevidend())
        .receiverId(detail.getReceiver().getReceiverId().getValue())
        .bank(detail.getBankAccount().getBank())
        .accountHolderName(detail.getBankAccount().getAccountHolderName())
        .accountNumber(detail.getBankAccount().getAccountNumber())
        .amount(detail.getAmount().getAmount())
        .transferProofUrl(detail.getTransferProofUrl())
        .build();
  }

  public ProfitDistribution profitDistributionEntityToProfitDistribution(ProfitDistributionEntity entity) {
    return ProfitDistribution.builder()
        .id(new ProfitDistributionId(entity.getId()))
        .managerId(new UserId(entity.getManagerId()))
        .projectId(new ProjectId(entity.getProjectId()))
        .projectProfit(new Money(entity.getProjectProfit()))
        .details(entity.getDetails().stream().map(this::profitDistributionDetailEntityToProfitDistributionDetail)
            .collect(Collectors.toList()))
        .distributedAt(entity.getDistributedAt())
        .status(entity.getStatus())
        .build();
  }

  public ProfitDistributionDetail profitDistributionDetailEntityToProfitDistributionDetail(
      ProfitDistributionDetailEntity entity) {
    return ProfitDistributionDetail.builder()
        .id(new ProfitDistributionDetailId(entity.getId()))
        .profitDistributionId(new ProfitDistributionId(entity.getProfitDistribution().getId()))
        .receiver(ShareHolder.builder()
            .deviden(entity.getDevidend())
            .receiverId(new UserId(entity.getReceiverId()))
            .type(entity.getType())
            .build())
        .bankAccount(new BankAccount(entity.getBank(), entity.getAccountNumber(), entity.getAccountHolderName()))
        .amount(new Money(entity.getAmount()))
        .transferProofUrl(entity.getTransferProofUrl())
        .build();
  }

  public OutboxEntity fundraisingRegisteredOutboxMessageToFundraisingRegisteredOutboxEntity(
      FundraisingRegisteredOutboxMessage outbox) {
    return OutboxEntity.builder()
        .id(outbox.getId())
        .version(outbox.getVersion())
        .type(OutboxType.FUNDRAISING_REGISTERED)
        .outboxStatus(outbox.getOutboxStatus())
        .payload(outbox.getPayload())
        .createdAt(outbox.getCreatedAt())
        .processedAt(outbox.getProcessedAt())
        .build();
  }

  public OutboxEntity closeFundraisingRequestedOutboxMessageToCloseFundraisingRequestedOutboxEntity(
      CloseFundraisingRequestedOutboxMessage outbox) {
    return OutboxEntity.builder()
        .id(outbox.getId())
        .version(outbox.getVersion())
        .type(OutboxType.CLOSE_FUNDRAISING_REQUESTED)
        .outboxStatus(outbox.getOutboxStatus())
        .payload(outbox.getPayload())
        .createdAt(outbox.getCreatedAt())
        .processedAt(outbox.getProcessedAt())
        .build();
  }

  public FundraisingRegisteredOutboxMessage fundraisingRegisteredOutboxEntityToFundraisingRegisteredOutboxMessage(
      OutboxEntity entity) {
    return FundraisingRegisteredOutboxMessage.builder()
        .id(entity.getId())
        .payload(entity.getPayload())
        .version(entity.getVersion())
        .outboxStatus(entity.getOutboxStatus())
        .createdAt(entity.getCreatedAt())
        .processedAt(entity.getProcessedAt())
        .build();
  }

  public CloseFundraisingRequestedOutboxMessage closeFundraisingRequestedOutboxEntityToCloseFundraisingRqeustedOutboxMessage(
      OutboxEntity entity) {
    return CloseFundraisingRequestedOutboxMessage.builder()
        .id(entity.getId())
        .outboxStatus(entity.getOutboxStatus())
        .payload(entity.getPayload())
        .version(entity.getVersion())
        .createdAt(entity.getCreatedAt())
        .processedAt(entity.getProcessedAt())
        .build();
  }

  private String writeDetailAsJsonString(Map<String, String> detail) {
    if (detail == null) {
      detail = new HashMap<>();
    }

    try {
      return objectMapper.writeValueAsString(detail);
    } catch (JsonProcessingException e) {
      throw new ProjectDomainException("Unable to stringify JSON", e);
    }
  }

  private Map<String, String> readDetailFromJsonString(String detail) {
    if (detail == null || detail.isEmpty()) {
      return new HashMap<>();
    }

    TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
    };

    try {
      return objectMapper.readValue(detail, typeRef);
    } catch (Exception e) {
      throw new ProjectDomainException("Unable to parse JSON", e);
    }

  }

}
