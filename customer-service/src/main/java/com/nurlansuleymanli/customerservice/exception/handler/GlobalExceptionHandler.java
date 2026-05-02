package com.nurlansuleymanli.customerservice.exception.handler;

import com.nurlansuleymanli.customerservice.exception.CustomerExistException;
import com.nurlansuleymanli.customerservice.exception.CustomerNotFoundException;
import com.nurlansuleymanli.customerservice.exception.EmailAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.emitter.Emitable;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomerExistException.class)
    public ResponseEntity<?> handleCustomerExistException(CustomerExistException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<?> handleCustomerNotFoundException(CustomerNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> handleEmailExistException(EmailAlreadyExistException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }


}
