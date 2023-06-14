package com.temantani.land.service.domain.dto.query;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HeightData {

  private BigDecimal value;
  private String unit;

}
