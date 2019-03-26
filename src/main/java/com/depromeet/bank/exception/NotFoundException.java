package com.depromeet.bank.exception;

/**
 * uri 에 해당하는 resource 가 존재하지 않는 경우, 상태 코드 404로 응답합니다
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
