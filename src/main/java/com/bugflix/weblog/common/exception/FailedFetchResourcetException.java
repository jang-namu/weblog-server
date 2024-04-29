package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class FailedFetchResourcetException extends RuntimeException {
    public FailedFetchResourcetException(Errors errors) {
        super(errors.getDescription());
    }
}
