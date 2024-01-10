package com.bugflix.weblog.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "토큰 발급 응답 DTO")
@Getter
public class TokenResponse {

    @Schema(description = "액세스 토큰", example = "RANDOM_TOKEN_VALUE")
    private String accessToken;

    @Schema(description = "액세스 토큰 재발급을 위한 토큰", example = "RANDOM_TOKEN_VALUE")
    private String refreshToken;

    @Builder
    private TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
