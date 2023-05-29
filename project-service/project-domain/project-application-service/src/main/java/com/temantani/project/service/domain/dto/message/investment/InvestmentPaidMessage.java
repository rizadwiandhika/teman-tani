package com.temantani.project.service.domain.dto.message.investment;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InvestmentPaidMessage {
  private UUID investemntId;
  private UUID projectId;
  private UUID investorId;
  private BigDecimal amount;
}
