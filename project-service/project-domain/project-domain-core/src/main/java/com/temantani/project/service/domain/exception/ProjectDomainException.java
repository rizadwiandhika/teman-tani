package com.temantani.project.service.domain.exception;

public class ProjectDomainException extends RuntimeException {

  public ProjectDomainException(String message) {
    super(message);
  }

  public ProjectDomainException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProjectDomainException(Throwable cause) {
    super(cause);
  }

}
