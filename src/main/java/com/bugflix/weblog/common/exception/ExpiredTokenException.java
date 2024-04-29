package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException(Errors errors) {
        super(errors.getDescription());
    }
}
