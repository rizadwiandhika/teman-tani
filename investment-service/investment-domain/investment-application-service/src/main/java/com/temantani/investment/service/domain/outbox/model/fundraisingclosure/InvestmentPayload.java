package com.temantani.investment.service.domain.outbox.model.fundraisingclosure;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvestmentPayload {
  private String investmentId;
  private String investorId;
  private BigDecimal amount;
}
