package com.ppapierz.cashmachine.exception;

public class CashBanknoteLimitException extends RuntimeException {
    public CashBanknoteLimitException(int amount) {
        super("Cash Bank note Limit exceeds! (" + amount + ")");
    }
}
