package com.depromeet.bank.exception;

/**
 * 요청이 잘못된 경우 400 상태코드로 응답합니다.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
