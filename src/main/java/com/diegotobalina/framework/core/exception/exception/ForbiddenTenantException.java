package com.diegotobalina.framework.core.exception.exception;

public class ForbiddenTenantException extends RuntimeException {
  public ForbiddenTenantException(String message) {
    super(message);
  }
}
