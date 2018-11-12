package com.wink.gateway.support;

public class NotAuthException extends RuntimeException {
    public NotAuthException(String message) {
        super(message);
    }

    public NotAuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
