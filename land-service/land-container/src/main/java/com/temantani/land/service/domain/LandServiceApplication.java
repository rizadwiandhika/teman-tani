package com.temantani.land.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.temantani.land.service.dataaccess" })
@EntityScan(basePackages = { "com.temantani.land.service.dataaccess" })
@SpringBootApplication(scanBasePackages = { "com.temantani" })
public class LandServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LandServiceApplication.class, args);
  }

}
