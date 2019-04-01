package com.depromeet.bank.exception;

/**
 * 구글 API 요청에 실패한 경우 던지는 예외
 */
public class GoogleApiFailedException extends ServiceUnavailableException {
    public GoogleApiFailedException() {
    }

    public GoogleApiFailedException(String message) {
        super(message);
    }
}
