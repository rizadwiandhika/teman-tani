package com.temantani.kafka.land.json.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LandRegisteredJsonModel {
  @JsonProperty
  private String landId;

  @JsonProperty
  private String ownerId;

  @JsonProperty
  private String approverId;

  @JsonProperty
  private ZonedDateTime approvedAt;

  @JsonProperty
  private ZonedDateTime proposedAt;

  @JsonProperty
  private String harvestSuitabilities;

  @JsonProperty
  private BigDecimal groundHeightValue;

  @JsonProperty
  private String groundHeightUnit;

  @JsonProperty
  private BigDecimal soilPh;

  @JsonProperty
  private String waterAvailabilityStatus;

  @JsonProperty
  private String landUsageHistory;

  @JsonProperty
  private String landStatus;

  @JsonProperty
  private BigDecimal areaValue;

  @JsonProperty
  private String areaUnit;

  @JsonProperty
  private String street;

  @JsonProperty
  private String city;

  @JsonProperty
  private String postalCode;

  @JsonProperty
  private String certificateUrl;

  @JsonProperty
  private String photos;

}
