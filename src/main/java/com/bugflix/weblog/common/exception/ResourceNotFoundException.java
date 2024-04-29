package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Errors errors) {
        super(errors.getDescription());
    }
}
