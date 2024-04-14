package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class FailedFetchResourceException extends RuntimeException {
    public FailedFetchResourceException(Errors errors) {
        super(errors.getDescription());
    }
}
