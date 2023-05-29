package com.temantani.land.service.domain.outbox.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LandRegisteredEventPayload {

  private UUID landId;
  private UUID ownerId;
  private UUID approverId;
  private ZonedDateTime approvedAt;
  private ZonedDateTime proposedAt;
  private String harvestSuitabilities;
  private BigDecimal groundHeightValue;
  private String groundHeightUnit;
  private BigDecimal soilPh;
  private String waterAvailabilityStatus;
  private String landUsageHistory;
  private String landStatus;
  private BigDecimal areaValue;
  private String areaUnit;
  private String street;
  private String city;
  private String postalCode;
  private String certificateUrl;
  private String photos;

}
