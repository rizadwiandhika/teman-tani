package com.temantani.kafka.investment.json.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class InvestmentJsonModel {
  @JsonProperty
  private String investmentId;

  @JsonProperty
  private String investorId;

  @JsonProperty
  private BigDecimal amount;
}
