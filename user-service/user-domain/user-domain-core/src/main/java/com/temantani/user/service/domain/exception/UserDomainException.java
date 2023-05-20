package com.temantani.user.service.domain.exception;

import com.temantani.domain.exception.DomainException;

public class UserDomainException extends DomainException {

  public UserDomainException(String message) {
    super(message);
  }

  public UserDomainException(String message, Throwable cause) {
    super(message, cause);
  }

}
