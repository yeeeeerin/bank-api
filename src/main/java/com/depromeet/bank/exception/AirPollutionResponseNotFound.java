package com.depromeet.bank.exception;

public class AirPollutionResponseNotFound extends RuntimeException {
    public AirPollutionResponseNotFound() { };

    public AirPollutionResponseNotFound(String message) {
        super(message);
    }
}
