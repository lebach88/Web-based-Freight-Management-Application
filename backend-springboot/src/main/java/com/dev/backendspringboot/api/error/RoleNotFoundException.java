package com.dev.backendspringboot.api.error;

public class RoleNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 423462857290752L;

    public RoleNotFoundException() {
    }
    public RoleNotFoundException(String message) {
        super(message);
    }
    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public RoleNotFoundException(Throwable cause) {
        super(cause);
    }
    public RoleNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
