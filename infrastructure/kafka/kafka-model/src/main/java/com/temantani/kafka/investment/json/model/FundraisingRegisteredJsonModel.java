package com.temantani.kafka.investment.json.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class FundraisingRegisteredJsonModel {
  @JsonProperty

  private String id;
  @JsonProperty

  private String landId;
  @JsonProperty

  private String description;
  @JsonProperty

  private String harvest;
  @JsonProperty

  private BigDecimal fundraisingTarget;
  @JsonProperty

  private ZonedDateTime fundraisingDeadline;
  @JsonProperty

  private ZonedDateTime estimatedFinished;
  @JsonProperty

  private ZonedDateTime createdAt;
}
