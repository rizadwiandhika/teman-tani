package com.temantani.land.service.domain.dto.approval;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRequest {
  @NotNull
  private String approvalStatus;

  @NotNull
  private List<String> harvestSuitabilities;

  @NotNull
  private BigDecimal groundHeightValue;

  @NotNull
  private String groundHeightUnit;

  @NotNull
  private BigDecimal soilPh;

  @NotNull
  private String waterAvailabilityStatus;

  @NotNull
  private String landUsageHistory;

}
