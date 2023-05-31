package com.temantani.investment.service.domain.outbox.model.investmentpaid;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InvestmentPaidEventPayload {

  private UUID investmentId;
  private UUID investorId;
  private UUID projectId;
  private BigDecimal amount;

}
