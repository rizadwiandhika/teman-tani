package com.temantani.project.service.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.temantani.project.service.domain.DomainService;
import com.temantani.project.service.domain.DomainServiceImpl;

@Configuration
public class ProjectServiceBeanConfiguration {

  @Bean
  public DomainService domainService() {
    return new DomainServiceImpl();
  }

}
