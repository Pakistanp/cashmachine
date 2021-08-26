package com.ppapierz.cashmachine.exception;

public class CardMoneyLimitExceedsException extends RuntimeException {
    public CardMoneyLimitExceedsException() {
        super("Card money Limit exceeds!");
    }
}
