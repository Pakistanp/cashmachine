package com.ppapierz.cashmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CardMoneyLimitExceedsAdvice {

    @ExceptionHandler(CardMoneyLimitExceedsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String cashLimitExceedsHandler(CardMoneyLimitExceedsException exception) {
        return exception.getMessage();
    }
}
