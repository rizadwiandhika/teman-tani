package com.temantani.investment.service.domain.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.domain.valueobject.UserId;
import com.temantani.investment.service.domain.dto.create.CreateInvestmentRequest;
import com.temantani.investment.service.domain.dto.message.CreateInvestorMessage;
import com.temantani.investment.service.domain.entity.Investment;
import com.temantani.investment.service.domain.entity.Investor;
import com.temantani.investment.service.domain.event.InvestmentPaidEvent;
import com.temantani.investment.service.domain.outbox.model.InvestmentPaidEventPayload;

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

}
