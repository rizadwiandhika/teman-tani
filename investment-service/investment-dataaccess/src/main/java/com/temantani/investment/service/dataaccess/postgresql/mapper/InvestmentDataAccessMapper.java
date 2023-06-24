package com.temantani.investment.service.dataaccess.postgresql.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.InvestmentId;
import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.dataaccess.postgresql.entity.FundraisingClosureOutboxEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.InvestmentEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.InvestorEntity;
import com.temantani.investment.service.dataaccess.postgresql.entity.FundraisingEntity;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureOutboxMessage;

@Component
public class InvestmentDataAccessMapper {

  public FundraisingEntity projectToProjectEntity(Fundraising project) {
    FundraisingEntity projectEntity = FundraisingEntity.builder()
        .id(project.getId().getValue())
        .version(project.getVersion())
        .status(project.getStatus())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .bookedFunds(project.getBookedFunds().getAmount())
        .collectedFunds(project.getCollectedFunds().getAmount())
        .description(project.getDescription())
        .tenorDeadline(project.getTenorDeadline())
        .createdAt(project.getCreatedAt())
        .investments(project.getInvestments() == null ? null
            : project.getInvestments().stream().map(this::investmentToInvestmentEntity).collect(Collectors.toList()))
        .build();

    projectEntity.getInvestments().forEach(i -> i.setFundraising(projectEntity));
    return projectEntity;
  }

  public Fundraising projectEntityToProject(FundraisingEntity entity) {
    return Fundraising.builder()
        .id(new ProjectId(entity.getId()))
        .version(entity.getVersion())
        .status(entity.getStatus())
        .fundraisingTarget(new Money(entity.getFundraisingTarget()))
        .bookedFunds(new Money(entity.getBookedFunds()))
        .collectedFunds(new Money(entity.getCollectedFunds()))
        .description(entity.getDescription())
        .tenorDeadline(entity.getTenorDeadline())
        .createdAt(entity.getCreatedAt())
        .investments(entity.getInvestments() == null ? new ArrayList<>()
            : entity.getInvestments().stream().map((i) -> this.investmentEntityToInvestment(i, entity.getId()))
                .collect(Collectors.toList()))
        .build();
  }

  // Investment to InvestmentEntity method
  public InvestmentEntity investmentToInvestmentEntity(Investment investment) {
    return InvestmentEntity.builder()
        .id(investment.getId().getValue())
        .version(investment.getVersion())
        .investorId(investment.getInvestorId().getValue())
        .amount(investment.getAmount().getAmount())
        .expiredAt(investment.getExpiredAt())
        .createdAt(investment.getCreatedAt())
        .status(investment.getStatus())
        .failureReasons(investment.getFailureReasons() == null ? null
            : String.join(Investment.FAILURE_REASONS_DELIMITER, investment.getFailureReasons()))
        .build();
  }

  public Investment investmentEntityToInvestment(InvestmentEntity investmentEntity, UUID projectId) {
    return Investment.builder()
        .id(new InvestmentId(investmentEntity.getId()))
        .version(investmentEntity.getVersion())
        .projectId(new ProjectId(projectId))
        .investorId(new UserId(investmentEntity.getInvestorId()))
        .amount(new Money(investmentEntity.getAmount()))
        .expiredAt(investmentEntity.getExpiredAt())
        .createdAt(investmentEntity.getCreatedAt())
        .status(investmentEntity.getStatus())
        .failureReasons(investmentEntity.getFailureReasons() == null
            ? null
            : Arrays.asList(investmentEntity.getFailureReasons().split(Investment.FAILURE_REASONS_DELIMITER)))
        .build();
  }

  public InvestorEntity investorToInvestorEntity(Investor investor) {
    return InvestorEntity.builder()
        .id(investor.getId().getValue())
        .name(investor.getName())
        .email(investor.getEmail())
        .profilePictureUrl(investor.getProfilePictureUrl())
        .build();
  }

  public Investor investorEntityToInvestor(InvestorEntity entity) {
    return Investor.builder()
        .id(new UserId(entity.getId()))
        .name(entity.getName())
        .email(entity.getEmail())
        .profilePictureUrl(entity.getProfilePictureUrl())
        .build();
  }

  public FundraisingClosureOutboxEntity fundraisingClosureOutboxMessageToFundraisingClosureOutboxEntity(
      FundraisingClosureOutboxMessage outbox) {
    return FundraisingClosureOutboxEntity.builder()
        .id(outbox.getId())
        .version(outbox.getVersion())
        .outboxStatus(outbox.getOutboxStatus())
        .payload(outbox.getPayload())
        .createdAt(outbox.getCreatedAt())
        .processedAt(outbox.getProcessedAt())
        .build();
  }

  public FundraisingClosureOutboxMessage fundraisingClosureOutboxEntityToFundraisingClosureOutboxMessage(
      FundraisingClosureOutboxEntity entity) {
    return FundraisingClosureOutboxMessage.builder()
        .id(entity.getId())
        .version(entity.getVersion())
        .outboxStatus(entity.getOutboxStatus())
        .payload(entity.getPayload())
        .createdAt(entity.getCreatedAt())
        .processedAt(entity.getProcessedAt())
        .build();
  }

}
