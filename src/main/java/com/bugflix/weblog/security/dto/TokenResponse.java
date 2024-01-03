package com.bugflix.weblog.security.dto;

import lombok.Getter;

@Getter
public class TokenResponse {
    private String token;

    private TokenResponse(String token) {
        this.token = token;
    }

    public static TokenResponse from(String token) {
        return new TokenResponse(token);
    }
}
