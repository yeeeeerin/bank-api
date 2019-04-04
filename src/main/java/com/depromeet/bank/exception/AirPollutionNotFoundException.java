package com.depromeet.bank.exception;

public class AirPollutionNotFoundException extends RuntimeException {
    public AirPollutionNotFoundException() { };

    public AirPollutionNotFoundException(String message) {
        super(message);
    }
}
