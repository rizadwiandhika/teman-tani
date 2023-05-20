package com.temantani.investment.service.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.temantani.investment.service.domain.InvestmentDomainService;
import com.temantani.investment.service.domain.InvestmentDomainServiceImpl;

@Configuration
public class InvestmentServiceBeanConfiguration {

  @Bean
  public InvestmentDomainService investmentDomainService() {
    return new InvestmentDomainServiceImpl();
  }

}
