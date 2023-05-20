package com.temantani.investment.service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MidtransRedirectDTO {
  private String status_code;
  private String status_message;
  private String bank;
  private String transaction_id;
  private String order_id;
  private String transaction_status;
  private String fraud_status;
}
