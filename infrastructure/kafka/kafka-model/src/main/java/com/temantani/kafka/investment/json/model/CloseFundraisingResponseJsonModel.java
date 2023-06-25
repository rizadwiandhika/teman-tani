package com.temantani.kafka.investment.json.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class CloseFundraisingResponseJsonModel {
  @JsonProperty
  private String status;

  @JsonProperty
  private String projectId;

  @JsonProperty
  private List<InvestmentJsonModel> investments;
}
