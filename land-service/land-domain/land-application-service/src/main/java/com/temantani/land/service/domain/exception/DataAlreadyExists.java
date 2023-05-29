package com.temantani.land.service.domain.exception;

public class DataAlreadyExists extends RuntimeException {

  public DataAlreadyExists(String message) {
    super(message);
  }

  public DataAlreadyExists(String message, Throwable cause) {
    super(message, cause);
  }

}
