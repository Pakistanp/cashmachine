package com.ppapierz.cashmachine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CashBanknoteLimitAdvice {

    @ExceptionHandler(CashBanknoteLimitException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String cashLimitExceedsHandler(CashBanknoteLimitException exception) {
        return exception.getMessage();
    }
}
