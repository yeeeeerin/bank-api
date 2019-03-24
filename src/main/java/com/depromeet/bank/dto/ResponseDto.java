package com.depromeet.bank.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto<T> {

    int status;

    String message;

    T response;

    public ResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
