package com.temantani.investment.service.domain.exception;

public class InvestorAlreadyExistsException extends RuntimeException {

  public InvestorAlreadyExistsException(String message) {
    super(message);
  }

  public InvestorAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

}
