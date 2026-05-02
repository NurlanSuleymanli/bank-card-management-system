package com.nurlansuleymanli.customerservice.exception.handler;

import com.nurlansuleymanli.customerservice.exception.CustomerExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomerExistException.class)
    public ResponseEntity<?> handleCustomerExistException(CustomerExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }


}
