package com.temantani.user.service.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.temantani.user.service.domain.UserDomainService;
import com.temantani.user.service.domain.UserDomainServiceImpl;

@Configuration
public class UserServiceBeanConfiguration {

  @Bean
  public UserDomainService userDomainService() {
    return new UserDomainServiceImpl();
  }

}
