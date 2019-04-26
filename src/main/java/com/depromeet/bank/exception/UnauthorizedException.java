package com.depromeet.bank.exception;

/**
 * token 이 없거나 유효하지 않은 경우, 상태코드 401로 응답합니다.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
