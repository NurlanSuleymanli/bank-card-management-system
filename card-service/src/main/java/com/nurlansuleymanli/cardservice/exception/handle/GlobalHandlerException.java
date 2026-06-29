package com.nurlansuleymanli.cardservice.exception.handle;

import com.nurlansuleymanli.cardservice.exception.CardLimitExceededException;
import com.nurlansuleymanli.cardservice.exception.CardNotFoundException;
import com.nurlansuleymanli.cardservice.exception.CardNumberGeneratorException;
import com.nurlansuleymanli.cardservice.exception.UnsupportedCardOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(CardNumberGeneratorException.class)
    public ResponseEntity<?> handleCardNumberGeneratorException(CardNumberGeneratorException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message:", e.getMessage()));
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<?> handleCardNotFoundException(CardNotFoundException e ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message:", e.getMessage()));
    }

    @ExceptionHandler(CardLimitExceededException.class)
    public ResponseEntity<?> handleCardLimitExceededException(CardLimitExceededException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message:", e.getMessage()));
    }

    @ExceptionHandler(UnsupportedCardOperationException.class)
    public ResponseEntity<?> handleUnsupportedCardOperationException(UnsupportedCardOperationException e){
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(Map.of("message:", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {

        String details = e.getBindingResult().getFieldErrors().stream()
                .map(feild -> "'" + feild.getField() + "': " + feild.getDefaultMessage())
                .collect(Collectors.joining("; "));

        String message = details.isBlank()
                ? "The submitted data is invalid. Please check your input and try again."
                : "Validation failed — " + details;

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "An unexpected error occurred. Please try again later."));
    }

}
