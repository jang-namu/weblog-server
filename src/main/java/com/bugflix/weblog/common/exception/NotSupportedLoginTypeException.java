package com.bugflix.weblog.common.exception;

import com.bugflix.weblog.common.Errors;

public class NotSupportedLoginTypeException extends RuntimeException {
    public NotSupportedLoginTypeException(Errors errors) {
        super(errors.getDescription());
    }
}
