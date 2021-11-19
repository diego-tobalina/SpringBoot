package com.xbidi.spring.exception.exception;

public class ForbiddenTenantException extends Exception {
  public ForbiddenTenantException(String message) {
    super(message);
  }
}
