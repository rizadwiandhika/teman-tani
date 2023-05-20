package com.temantani.land.service.domain.valueobject;

import java.math.BigDecimal;

public class Area {
  private final BigDecimal value;
  private final AreaUnits unit;

  public Area(BigDecimal value, AreaUnits unit) {
    this.value = value;
    this.unit = unit;
  }

  public BigDecimal getValueInHectare() {
    return value;
  }

  public AreaUnits getUnit() {
    return unit;
  }

  public enum AreaUnits {
    HECTARE
  }

}
