package com.temantani.land.service.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.handler.ErrorDTO;
import com.temantani.handler.GlobalExceptionHandler;
import com.temantani.land.service.domain.exception.LandDomainException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class LandGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(value = { LandDomainException.class })
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(LandDomainException e) {
    log.error(e.getMessage(), e);
    // log.error(e.getMessage());

    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(value = { DataNotFoundException.class })
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorDTO handleException(DataNotFoundException e) {
    log.error(e.getMessage(), e);
    // log.error(e.getMessage());

    return ErrorDTO.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build();
  }

}
