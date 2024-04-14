package com.bugflix.weblog.common;

import com.bugflix.weblog.common.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity handleExpiredTokenException(ExpiredTokenException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(FailedFetchResourceException.class)
    public ResponseEntity handleFailedFetchResourceException(FailedFetchResourceException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(FailedTokenExchangeException.class)
    public ResponseEntity handleFailedTokenExchangeException(FailedTokenExchangeException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoOwnershipException.class)
    public ResponseEntity handleNoOwnershipException(NoOwnershipException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotSupportedLoginTypeException.class)
    public ResponseEntity handleNotSupportedLoginTypeException(NotSupportedLoginTypeException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.badRequest().build();
    }
}
