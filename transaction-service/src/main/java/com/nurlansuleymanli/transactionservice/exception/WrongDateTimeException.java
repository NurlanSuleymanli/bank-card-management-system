package com.nurlansuleymanli.transactionservice.exception;

public class WrongDateTimeException extends RuntimeException {
    public WrongDateTimeException(String message) {
        super(message);
    }
}
