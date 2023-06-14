package com.temantani.land.service.domain.dto.query;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AreaData {
  private BigDecimal valueInHectare;
  private String unit;
}
