package com.temantani.investment.service.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "investment-service")
public class InvestmentServiceConfigData {

  private String closeFundraisingResponseTopicName;

}
