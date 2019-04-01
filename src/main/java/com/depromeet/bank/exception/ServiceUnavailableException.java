package com.depromeet.bank.exception;

/**
 * 외부 API 요청에 실패한 경우 던지는 예외
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }
}
