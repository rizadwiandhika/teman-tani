package com.temantani.project.service.domain.dto.profitdistribution;

import java.math.BigDecimal;
import java.util.UUID;

import com.temantani.domain.valueobject.BankAccount;
import com.temantani.project.service.domain.valueobject.DistributionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfitDistributionDetailDto {

  private UUID id;
  private UUID profitDistributionId;
  private UUID receiverId;
  private DistributionType distributionType;
  private BigDecimal devidend;
  private BigDecimal amount;
  private BankAccount bankAccount;

}
