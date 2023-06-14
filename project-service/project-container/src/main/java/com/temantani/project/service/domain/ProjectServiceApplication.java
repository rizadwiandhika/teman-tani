package com.temantani.project.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.temantani" })
@EntityScan(basePackages = { "com.temantani.project.service.dataaccess" })
@EnableJpaRepositories(basePackages = { "com.temantani.project.service.dataaccess" })
public class ProjectServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProjectServiceApplication.class, args);
  }

}