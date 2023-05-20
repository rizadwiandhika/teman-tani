package com.temantani.land.service.domain.valueobject;

import java.math.BigDecimal;
import java.util.List;

import com.temantani.land.service.domain.exception.LandDomainException;

public class Assessment {
  private final List<String> harvestSuitabilities;
  private final Height groundHeight;
  private final BigDecimal soilPh;
  private final WaterAvailabilityStatus waterAvailabilityStatus;
  private final String landUsageHistory;

  public static final String HARVEST_SUITABILITIES_DELIMITER = ",";

  private Assessment(List<String> harvestSuitabilities, Height groundHeight, BigDecimal soilPh,
      WaterAvailabilityStatus waterAvailabilityStatus, String landUsageHistory) {
    if (soilPh.compareTo(BigDecimal.ZERO) < 0 || soilPh.compareTo(BigDecimal.valueOf(14)) > 0) {
      throw new LandDomainException("Soil pH should be between 0 and 14");
    }

    if (groundHeight == null || groundHeight.getValue().compareTo(BigDecimal.ZERO) < 0) {
      throw new LandDomainException("Ground height should be greater than 0");
    }

    if (harvestSuitabilities == null || harvestSuitabilities.isEmpty()) {
      throw new LandDomainException("Harvest suitabilities should not be empty");
    }

    this.harvestSuitabilities = harvestSuitabilities;
    this.groundHeight = groundHeight;
    this.soilPh = soilPh;
    this.waterAvailabilityStatus = waterAvailabilityStatus;
    this.landUsageHistory = landUsageHistory;
  }

  public List<String> getHarvestSuitabilities() {
    return harvestSuitabilities;
  }

  public Height getGroundHeight() {
    return groundHeight;
  }

  public BigDecimal getSoilPh() {
    return soilPh;
  }

  public WaterAvailabilityStatus getWaterAvailabilityStatus() {
    return waterAvailabilityStatus;
  }

  public String getLandUsageHistory() {
    return landUsageHistory;
  }

  public static Builder builder() {
    return new Builder();
  }

  // Builder
  public static class Builder {
    private List<String> harvestSuitabilities;
    private Height groundHeight;
    private BigDecimal soilPh;
    private WaterAvailabilityStatus waterAvailabilityStatus;
    private String landUsageHistory;

    public Builder harvestSuitabilities(List<String> harvestSuitabilities) {
      this.harvestSuitabilities = harvestSuitabilities;
      return this;
    }

    public Builder groundHeight(Height groundHeight) {
      this.groundHeight = groundHeight;
      return this;
    }

    public Builder soilPh(BigDecimal soilPh) {
      this.soilPh = soilPh;
      return this;
    }

    public Builder waterAvailabilityStatus(WaterAvailabilityStatus waterAvailabilityStatus) {
      this.waterAvailabilityStatus = waterAvailabilityStatus;
      return this;
    }

    public Builder landUsageHistory(String landUsageHistory) {
      this.landUsageHistory = landUsageHistory;
      return this;
    }

    public Assessment build() {
      return new Assessment(harvestSuitabilities, groundHeight, soilPh, waterAvailabilityStatus, landUsageHistory);
    }
  }

}
