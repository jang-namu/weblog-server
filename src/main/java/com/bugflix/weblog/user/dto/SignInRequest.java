package com.bugflix.weblog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인, 토큰 재발급 요청 DTO", requiredProperties = {"email", "password"})
@Getter
public class SignInRequest {

    @Schema(description = "이메일(로그인 ID)", example="bugflix19@example.com")
    private String email;

    @Schema(description = "비밀번호", example="test1234!")
    private String password;

}
