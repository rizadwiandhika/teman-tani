package com.temantani.user.service.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "user-service")
public class UserServiceConfigData {

  private String userTopicName;

}
