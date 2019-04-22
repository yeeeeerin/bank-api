package com.depromeet.bank.exception;

/**
 * 서버에서 에러가 발생하는 경우, 상태 코드 500 으로 응답합니다.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
    }

    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
