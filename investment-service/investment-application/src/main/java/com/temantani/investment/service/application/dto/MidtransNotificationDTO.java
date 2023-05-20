package com.temantani.investment.service.application.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MidtransNotificationDTO {

  @JsonProperty("status_code")
  private String statusCode;

  @JsonProperty("status_message")
  private String statusMessage;

  @JsonProperty("bank")
  private String bank;

  @JsonProperty("transaction_id")
  private String transactionId;

  @JsonProperty("order_id")
  private String orderId;

  @JsonProperty("transaction_status")
  private String transactionStatus;

  @JsonProperty("fraud_status")
  private String fraudStatus;

  @JsonProperty("signature_key")
  private String signatureKey;

  @JsonProperty("gross_amount")
  private BigDecimal grossAmount;

}
