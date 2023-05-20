package com.temantani.land.service.domain.dto.common;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {
  @NotNull
  private BigDecimal value;

  @NotNull
  private String unit;
}
