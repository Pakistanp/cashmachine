package com.ppapierz.cashmachine.exception;

public class CashLimitExceedsException extends RuntimeException {
    public CashLimitExceedsException(int amount) {
        super("Cash Limit exceeds! (" + amount + ")");
    }
}
