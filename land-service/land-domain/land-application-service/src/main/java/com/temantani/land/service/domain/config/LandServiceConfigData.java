package com.temantani.land.service.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "land-service")
public class LandServiceConfigData {
  private String landRegisteredTopicName;
}
