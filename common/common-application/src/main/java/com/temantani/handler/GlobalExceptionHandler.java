package com.temantani.handler;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorDTO handleException(Exception e) {
    log.error(e.getMessage(), e);
    // log.error(e.getMessage());

    return ErrorDTO.builder()
        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(e.getMessage())
        .build();
  }

  @ResponseBody
  @ExceptionHandler(ValidationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(ValidationException validationException) {
    ErrorDTO errorDTO;

    if (validationException instanceof ConstraintViolationException) {
      String violations = extractViolationFromException((ConstraintViolationException) validationException);
      log.error(violations, validationException);
      errorDTO = ErrorDTO.builder()
          .code(HttpStatus.BAD_REQUEST.value())
          .message(violations)
          .build();
    } else {
      String exceptionMessage = validationException.getMessage();
      log.error(exceptionMessage, validationException);
      errorDTO = ErrorDTO.builder()
          .code(HttpStatus.BAD_REQUEST.value())
          .message(exceptionMessage)
          .build();
    }

    return errorDTO;
  }

  @ResponseBody
  @ExceptionHandler(BindException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorDTO handleException(BindException ex) {
    String violations = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
        .collect(Collectors.joining(" -- "));

    log.error(violations, ex);

    return ErrorDTO.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .message(violations)
        .build();

  }

  private String extractViolationFromException(ConstraintViolationException validException) {
    return validException.getConstraintViolations()
        .stream()
        .map(ConstraintViolation::getMessage)
        .collect(Collectors.joining("--"));
  }

}
