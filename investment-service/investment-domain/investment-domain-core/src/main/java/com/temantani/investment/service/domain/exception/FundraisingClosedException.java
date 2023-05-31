package com.temantani.investment.service.domain.exception;

public class FundraisingClosedException extends InvestmentDomainException {
  public FundraisingClosedException(String message) {
    super(message);
  }

  public FundraisingClosedException(String message, Throwable cause) {
    super(message, cause);
  }
}
