package com.temantani.security.exceptionhandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private ObjectMapper objectMapper = new ObjectMapper();

  // This method will be called if there is a failure in attempting authentication
  // using AuthenticationManager
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    log.error("[CustomAuthenticationEntryPoint] unauthenticated tried to access resources");

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    Map<String, Object> data = new HashMap<>();
    data.put(
        "timestamp",
        Calendar.getInstance().getTime());
    data.put(
        "exception",
        exception.getMessage());
    data.put("from", "CustomAuthenticationEntryPoint");

    response.getOutputStream()
        .println(objectMapper.writeValueAsString(data));
  }
}
