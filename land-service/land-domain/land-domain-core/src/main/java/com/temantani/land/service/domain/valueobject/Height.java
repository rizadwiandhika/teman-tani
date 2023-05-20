package com.temantani.land.service.domain.valueobject;

import java.math.BigDecimal;

public class Height {
  private final BigDecimal value;
  private final HeightUnit unit;

  public Height(BigDecimal value, HeightUnit unit) {
    this.value = value;
    this.unit = unit;
  }

  public BigDecimal getValue() {
    return value;
  }

  public HeightUnit getUnit() {
    return unit;
  }

  public enum HeightUnit {
    METER
  }

}
