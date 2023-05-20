package com.temantani.user.service.domain.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.temantani.domain.valueobject.AdminRole;
import com.temantani.security.exceptionhandler.CustomAccessDeniedHandler;
import com.temantani.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.temantani.security.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class UserServiceSecurityConfiguration {

  @Bean
  public SecurityFilterChain securituConfig(
      HttpSecurity http,
      AuthenticationProvider authenticationProvider,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
      CustomAccessDeniedHandler customAccessDeniedHandler,
      JwtAuthFilter jwtAuthFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling((e) -> {
          e.authenticationEntryPoint(customAuthenticationEntryPoint);
          e.accessDeniedHandler(customAccessDeniedHandler);
        })
        .authorizeHttpRequests(auth -> {
          auth.antMatchers("/admins/**")
              .hasAnyRole(Arrays.stream(AdminRole.values()).map(Enum::name).toArray(String[]::new));
          auth.antMatchers("/users/**").authenticated();
          auth.anyRequest().permitAll();
        })
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    // AuthenticationProvider is Data Access Object (DAO) which responsible
    // to fetch the user detail and also encode password

    DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
    dao.setHideUserNotFoundExceptions(false);

    dao.setUserDetailsService(userDetailsService);
    dao.setPasswordEncoder(passwordEncoder);

    return dao;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean(value = "bcryptPasswordEncoder")
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
