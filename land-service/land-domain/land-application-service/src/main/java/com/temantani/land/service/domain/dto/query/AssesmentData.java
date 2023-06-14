package com.temantani.land.service.domain.dto.query;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AssesmentData {

  private List<String> harvestSuitabilities;
  private HeightData groundHeight;
  private BigDecimal soilPh;
  private String waterAvailabilityStatus;
  private String landUsageHistory;

}
