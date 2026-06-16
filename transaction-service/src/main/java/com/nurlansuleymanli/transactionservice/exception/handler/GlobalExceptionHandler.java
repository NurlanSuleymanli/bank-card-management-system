package com.nurlansuleymanli.transactionservice.exception.handler;

import com.nurlansuleymanli.transactionservice.exception.TransactionNotFoundException;
import com.nurlansuleymanli.transactionservice.exception.WrongDateTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<?> handleTransactionNotFoundException(TransactionNotFoundException ex) {
        log.error("TransactionNotFoundException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(WrongDateTimeException.class)
    public ResponseEntity<?> handleWrongDateTimeException(WrongDateTimeException ex) {
        log.error("WrongDateTimeException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {

        log.warn("Validation Error: {}", e.getMessage());

        String details = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> "'" + fe.getField() + "': " + fe.getDefaultMessage())
                .collect(Collectors.joining("; "));

        String message = details.isBlank()
                ? "The submitted data is invalid. Please check your input and try again."
                : "Validation failed — " + details;

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        log.error("Exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An unexpected error occurred. Please try again later."));
    }
}
