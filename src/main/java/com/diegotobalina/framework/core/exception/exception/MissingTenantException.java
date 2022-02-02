package com.diegotobalina.framework.core.exception.exception;

public class MissingTenantException extends RuntimeException {
    public MissingTenantException(String message) {
        super(message);
    }
}
