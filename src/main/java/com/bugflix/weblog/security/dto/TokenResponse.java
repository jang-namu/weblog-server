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

    @Schema(description = "사용자 고유 식별 번호", example = "115234")
    private Long userId;

    @Schema(description = "사용자 별칭", example = "weblog")
    private String nickname;

    @Schema(description = "사용자 이메일", example = "bugflix@example.com")
    private String email;

    @Builder
    public TokenResponse(String accessToken, String refreshToken, Long userId, String nickname, String email) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
    }
}
