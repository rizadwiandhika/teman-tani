package com.temantani.project.service.application.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.temantani.handler.ErrorDTO;
import com.temantani.handler.GlobalExceptionHandler;
import com.temantani.project.service.domain.exception.ProjectDomainException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ProjectGlobalExceptionHandler extends GlobalExceptionHandler {
  @ResponseBody
  @ExceptionHandler(value = { ProjectDomainException.class })
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(ProjectDomainException e) {
    log.error(e.getMessage(), e);

    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(e.getMessage())
        .build();
  }

}
