package com.depromeet.bank.exception;

/**
 * 값을 응답하지 않아도 되는 경우, 204 상태코드로 응답합니다.
 */
public class NoContentException extends RuntimeException {
    public NoContentException() {
        super();
    }
}
