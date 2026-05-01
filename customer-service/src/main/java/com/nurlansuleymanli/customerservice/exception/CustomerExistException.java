package com.nurlansuleymanli.customerservice.exception;

public class CustomerExistException extends RuntimeException {
    public CustomerExistException(String message) {
        super(message);
    }
}
