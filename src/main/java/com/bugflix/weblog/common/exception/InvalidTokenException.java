package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(Errors errors) {
        super(errors.getDescription());
    }
}
