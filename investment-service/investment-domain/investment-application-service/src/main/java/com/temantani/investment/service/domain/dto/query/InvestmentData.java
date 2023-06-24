package com.temantani.investment.service.domain.dto.query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.temantani.investment.service.domain.valueobject.InvestmentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InvestmentData {

  private String id;
  private String investorId;
  private String fundraisingId;
  private BigDecimal amount;
  private ZonedDateTime expiredAt;
  private ZonedDateTime createdAt;
  private InvestmentStatus status;

}
