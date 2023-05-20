package com.temantani.investment.service.domain.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temantani.domain.valueobject.Money;
import com.temantani.domain.valueobject.ProjectId;
import com.temantani.investment.service.domain.dto.CreateInvestmentRequest;
import com.temantani.investment.service.domain.entity.Investment;

@Component
public class InvestmentDataMapper {

  public Investment createInvestmentRequestToInvestment(CreateInvestmentRequest req) {
    return Investment.builder()
        .projectId(new ProjectId(UUID.fromString(req.getProjectId())))
        .investorId(req.getInvestorId())
        .amount(new Money(req.getAmount()))
        .build();
  }

}
