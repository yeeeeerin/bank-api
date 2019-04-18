package com.depromeet.bank.controller;

import com.depromeet.bank.dto.ResponseDto;
import com.depromeet.bank.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiControllerAdvice {
    public static final Logger log =  LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleBadRequestException(BadRequestException ex) {
        return ResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseDto.of(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseDto handleUnauthorizedException(UnauthorizedException ex) {
        if (!ex.getMessage().isEmpty()) {
            return ResponseDto.of(HttpStatus.UNAUTHORIZED, ex.getMessage());
        } else {
            return ResponseDto.of(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto handleNotFoundException(NotFoundException ex) {
        return ResponseDto.of(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ResponseDto handleServiceUnavailableException(ServiceUnavailableException ex) {
        return ResponseDto.of(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto handleBadRequestException(InternalServerErrorException ex) {
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto handleException(Exception ex) {
        log.error("internal server error", ex);
        return ResponseDto.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }


}
