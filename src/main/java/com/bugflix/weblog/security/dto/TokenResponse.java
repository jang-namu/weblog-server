package com.bugflix.weblog.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResponse {
    private String accessToken;

    private String refreshToken;

    @Builder
    private TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
