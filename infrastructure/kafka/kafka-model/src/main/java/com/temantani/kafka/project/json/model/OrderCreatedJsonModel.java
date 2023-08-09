package com.temantani.kafka.project.json.model;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCreatedJsonModel {
  private String orderId;
  private String projectId;
  private BigDecimal amount;

  @JsonProperty("order")
  private void unpackNameFromNestedObject(Map<String, Object> order) {
    orderId = order.get("id").toString();
    amount = new BigDecimal(order.get("amount").toString());
  }
}
