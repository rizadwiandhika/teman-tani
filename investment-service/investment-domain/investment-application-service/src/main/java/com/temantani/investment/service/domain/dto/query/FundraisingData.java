package com.temantani.investment.service.domain.dto.query;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.temantani.investment.service.domain.valueobject.FundraisingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FundraisingData {
  private String id;
  private FundraisingStatus status;
  private BigDecimal fundraisingTarget;
  private BigDecimal bookedFunds;
  private String description;
  private ZonedDateTime tenorDeadline;
  private ZonedDateTime createdAt;
}
