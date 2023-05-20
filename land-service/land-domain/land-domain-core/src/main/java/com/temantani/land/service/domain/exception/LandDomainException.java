package com.temantani.land.service.domain.exception;

import com.temantani.domain.exception.DomainException;

public class LandDomainException extends DomainException {

  public LandDomainException(String message) {
    super(message);
  }

  public LandDomainException(String message, Throwable cause) {
    super(message, cause);
  }

}
