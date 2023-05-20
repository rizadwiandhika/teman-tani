package com.temantani.investment.service.domain.config;

import static com.temantani.domain.valueobject.UserRole.INVESTOR;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.temantani.domain.valueobject.UserRole;
import com.temantani.security.exceptionhandler.CustomAccessDeniedHandler;
import com.temantani.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.temantani.security.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class InvestmentServiceSecurityConfiguration {

  @Bean
  public SecurityFilterChain securituConfig(
      HttpSecurity http,
      CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
      CustomAccessDeniedHandler customAccessDeniedHandler,
      JwtAuthFilter jwtAuthFilter) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        // .cors()
        // .and()
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling((e) -> {
          e.authenticationEntryPoint(customAuthenticationEntryPoint);
          e.accessDeniedHandler(customAccessDeniedHandler);
        })
        .authorizeHttpRequests(auth -> {
          // auth.antMatchers("/admins/**")
          // .hasAnyRole(Arrays.stream(AdminRole.values()).filter((e) -> e == ADMIN_SUPER)
          // .map(Enum::name).toArray(String[]::new));

          // TODO: update to be this role authenticated
          // auth.antMatchers("/investments/**")
          // .hasAnyRole(Arrays.stream(UserRole.values())
          // .filter((e) -> e == INVESTOR)
          // .map(Enum::name).toArray(String[]::new));
          auth.antMatchers("/investments/**").authenticated();

          auth.anyRequest().permitAll();
        })
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
