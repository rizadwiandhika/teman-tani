package com.temantani.investment.service.payment.midtrans.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "midtrans")
public class MidtransConfig {
  private String clientKey;
  private String serverKey;
  private Boolean isProduction;
}
