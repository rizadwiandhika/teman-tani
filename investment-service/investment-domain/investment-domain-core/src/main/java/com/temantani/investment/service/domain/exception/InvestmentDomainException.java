package com.temantani.investment.service.domain.exception;

import com.temantani.domain.exception.DomainException;

public class InvestmentDomainException extends DomainException {

  public InvestmentDomainException(String message) {
    super(message);
  }

  public InvestmentDomainException(String message, Throwable cause) {
    super(message, cause);
  }

}
