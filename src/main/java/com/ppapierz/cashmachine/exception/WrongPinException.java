package com.ppapierz.cashmachine.exception;

public class WrongPinException extends RuntimeException{
    public WrongPinException() {
        super("Entered pin is invalid!");
    }
}
