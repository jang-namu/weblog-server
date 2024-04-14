package com.bugflix.weblog.common;

public class CommonResponse<T> {
    private int code;
    private String message;
    private T data;

    private CommonResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
