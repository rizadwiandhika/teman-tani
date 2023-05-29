package com.temantani.investment.service.domain.exception;

public class InvestmentPaymentException extends InvestmentDomainException {
  public InvestmentPaymentException(String message) {
    super(message);
  }

  public InvestmentPaymentException(String message, Throwable cause) {
    super(message, cause);
  }
}
