package com.temantani.user.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.temantani.user.service.dataaccess" })
@EntityScan(basePackages = { "com.temantani.user.service.dataaccess" })
@SpringBootApplication(scanBasePackages = { "com.temantani" })
public class UserServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserServiceApplication.class, args);
  }

}
