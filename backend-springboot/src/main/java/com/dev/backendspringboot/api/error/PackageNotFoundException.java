package com.dev.backendspringboot.api.error;

public class PackageNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 423462857290752L;
    public PackageNotFoundException() {
    }

    public PackageNotFoundException(String message) {
        super(message);
    }

    public PackageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PackageNotFoundException(Throwable cause) {
        super(cause);
    }

    public PackageNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
