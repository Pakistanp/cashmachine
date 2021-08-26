package com.ppapierz.cashmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WrongPinAdvice {

    @ExceptionHandler(WrongPinException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String wrongPinHandler(WrongPinException exception) {
        return exception.getMessage();
    }
}
