package com.temantani.user.service.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.temantani.handler.ErrorDTO;
import com.temantani.handler.GlobalExceptionHandler;
import com.temantani.user.service.domain.exception.UserDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class UserGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public ErrorDTO handleException(BadCredentialsException e) {
    log.error(e.getMessage(), e);

    return ErrorDTO.builder()
        .code(HttpStatus.UNAUTHORIZED.value())
        .message("Invalid email or password")
        .build();
  }

  @ResponseBody
  @ExceptionHandler(UsernameNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorDTO handleException(UsernameNotFoundException e) {
    log.error(e.getMessage(), e);

    return ErrorDTO.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message("User not found")
        .build();
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = { UserDomainException.class })
  public ErrorDTO handle(UserDomainException exception) {
    log.error(exception.getMessage());

    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(exception.getMessage())
        .build();
  }
}