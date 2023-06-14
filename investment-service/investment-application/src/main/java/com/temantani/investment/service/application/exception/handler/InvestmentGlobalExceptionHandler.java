package com.temantani.investment.service.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.temantani.domain.exception.DataNotFoundException;
import com.temantani.handler.ErrorDTO;
import com.temantani.handler.GlobalExceptionHandler;
import com.temantani.investment.service.domain.exception.InvestmentDomainException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class InvestmentGlobalExceptionHandler extends GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(InvestmentDomainException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(InvestmentDomainException e) {
    log.error(e.getMessage(), e);

    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(DataNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorDTO handleException(DataNotFoundException e) {
    log.error(e.getMessage(), e);

    return ErrorDTO.builder()
        .code(HttpStatus.NOT_FOUND.value())
        .message(e.getMessage())
        .build();
  }

}
