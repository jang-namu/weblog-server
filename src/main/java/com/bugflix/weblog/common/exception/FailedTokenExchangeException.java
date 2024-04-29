package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class FailedTokenExchangeException extends RuntimeException {
    public FailedTokenExchangeException(Errors errors) {
        super(errors.getDescription());
    }
}
