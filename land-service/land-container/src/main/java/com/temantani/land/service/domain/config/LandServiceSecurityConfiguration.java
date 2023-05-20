package com.temantani.land.service.domain.config;

import static com.temantani.domain.valueobject.AdminRole.ADMIN_LANDOWNER;
import static com.temantani.domain.valueobject.AdminRole.ADMIN_SUPER;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.temantani.domain.valueobject.AdminRole;
import com.temantani.security.exceptionhandler.CustomAccessDeniedHandler;
import com.temantani.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.temantani.security.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class LandServiceSecurityConfiguration {

  @Bean
  public SecurityFilterChain securituConfig(
      HttpSecurity http,
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
              .hasAnyRole(Arrays.stream(AdminRole.values()).filter((e) -> e == ADMIN_SUPER || e == ADMIN_LANDOWNER)
                  .map(Enum::name).toArray(String[]::new));
          auth.antMatchers("/lands/**").authenticated();
          auth.anyRequest().permitAll();
        })
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

}
