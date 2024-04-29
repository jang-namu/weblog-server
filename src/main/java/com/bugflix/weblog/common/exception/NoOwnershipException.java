package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class NoOwnershipException extends RuntimeException {
    public NoOwnershipException(Errors errors) {
        super(errors.getDescription());
    }
}
