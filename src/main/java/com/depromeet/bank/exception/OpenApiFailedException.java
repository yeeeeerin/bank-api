package com.depromeet.bank.exception;

public class OpenApiFailedException extends RuntimeException {
    public OpenApiFailedException() { }

    public OpenApiFailedException(String message) {
        super(message);
    }
}
