package com.temantani.kafka.investment.json.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CloseFundraisingRequestJsonModel {
  @JsonProperty
  private String projectId;
}
