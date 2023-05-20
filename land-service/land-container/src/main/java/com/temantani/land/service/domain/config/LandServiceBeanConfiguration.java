package com.temantani.land.service.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.temantani.land.service.domain.LandDomainService;
import com.temantani.land.service.domain.LandDomainServiceImpl;

@Configuration
public class LandServiceBeanConfiguration {

  @Bean
  public LandDomainService landDomainService() {
    return new LandDomainServiceImpl();
  }
}
