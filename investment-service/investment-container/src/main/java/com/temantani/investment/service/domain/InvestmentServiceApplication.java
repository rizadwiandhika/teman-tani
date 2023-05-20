package com.temantani.investment.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.temantani" })
@EnableJpaRepositories(basePackages = { "com.temantani.investment.service.dataaccess" })
@EntityScan(basePackages = { "com.temantani.investment.service.dataaccess" })
public class InvestmentServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(InvestmentServiceApplication.class, args);
  }
}
