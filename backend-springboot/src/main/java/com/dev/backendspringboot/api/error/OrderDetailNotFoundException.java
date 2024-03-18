package com.dev.backendspringboot.api.error;

public class OrderDetailNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 423462857290752L;

    public OrderDetailNotFoundException() {
    }

    public OrderDetailNotFoundException(String message) {
        super(message);
    }

    public OrderDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDetailNotFoundException(Throwable cause) {
        super(cause);
    }

    public OrderDetailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
