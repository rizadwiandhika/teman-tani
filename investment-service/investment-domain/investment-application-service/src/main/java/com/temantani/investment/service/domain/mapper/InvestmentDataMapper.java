package com.temantani.investment.service.domain.mapper;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.create.CreateInvestmentRequest;
import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;
import com.temantani.investment.service.domain.dto.query.FundraisingData;
import com.temantani.investment.service.domain.dto.query.InvestmentData;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.entity.Fundraising;
import com.temantani.investment.service.domain.event.InvestmentPaidEvent;
import com.temantani.investment.service.domain.event.ProjectClosureEvent;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.FundraisingClosureEventPayload;
import com.temantani.investment.service.domain.outbox.model.fundraisingclosure.InvestmentPayload;
import com.temantani.investment.service.domain.outbox.model.investmentpaid.InvestmentPaidEventPayload;
import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

@Component
public class InvestmentDataMapper {

  public Investment createInvestmentRequestToInvestment(CreateInvestmentRequest req) {
    return Investment.builder()
        .projectId(new ProjectId(UUID.fromString(req.getProjectId())))
        .investorId(req.getInvestorId())
        .amount(new Money(req.getAmount()))
        .build();
  }

  public Investor createInvestorMessageToInvestor(CreateInvestorMessage message) {
    return Investor.builder()
        .id(new UserId(message.getUserId()))
        .name(message.getName())
        .email(message.getEmail())
        .profilePictureUrl(message.getProfilePictureUrl())
        .build();
  }

  public InvestmentPaidEventPayload investmentPaidEventToInvestmentPaidEventPayload(InvestmentPaidEvent domainEvent) {
    return InvestmentPaidEventPayload.builder()
        .investmentId(domainEvent.getInvestment().getId().getValue())
        .investorId(domainEvent.getInvestment().getInvestorId().getValue())
        .projectId(domainEvent.getInvestment().getProjectId().getValue())
        .amount(domainEvent.getInvestment().getAmount().getAmount())
        .build();
  }

  public FundraisingClosureEventPayload projectClosureEventToFundraisingClosureEventPayload(ProjectClosureEvent event) {
    return FundraisingClosureEventPayload.builder()
        .status(event.getStatus().name())
        .projectId(event.getProject().getId().getValue().toString())
        .investments(event.getProject().getInvestments() == null ? new ArrayList<>()
            : event.getProject().getInvestments()
                .stream()
                .filter((i) -> i.getStatus() == InvestmentStatus.PAID)
                .map((i) -> InvestmentPayload.builder()
                    .investmentId(i.getId().getValue().toString())
                    .investorId(i.getInvestorId().getValue().toString())
                    .amount(i.getAmount().getAmount())
                    .build())
                .collect(Collectors.toList()))
        .build();
  }

  public FundraisingData projectToFundraisingData(Fundraising project) {
    return FundraisingData.builder()
        .id(project.getId().getValue().toString())
        .status(project.getStatus())
        .fundraisingTarget(project.getFundraisingTarget().getAmount())
        .bookedFunds(project.getBookedFunds().getAmount())
        .description(project.getDescription())
        .tenorDeadline(project.getTenorDeadline())
        .createdAt(project.getCreatedAt())
        .build();
  }

  public InvestmentData investmentToInvestmentData(Investment investment) {
    return InvestmentData.builder()
        .id(investment.getId().getValue().toString())
        .investorId(investment.getInvestorId().getValue().toString())
        .fundraisingId(investment.getProjectId().getValue().toString())
        .amount(investment.getAmount().getAmount())
        .expiredAt(investment.getExpiredAt())
        .status(investment.getStatus())
        .build();
  }

}
