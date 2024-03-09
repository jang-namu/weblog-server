package com.bugflix.weblog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "로그인, 토큰 재발급 요청 DTO", requiredProperties = {"email", "password"})
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInRequest {

    @Schema(description = "이메일(로그인 ID)", example="bugflix19@example.com")
    @NotNull
    private String email;

    @Schema(description = "비밀번호", example="test1234!")
    @NotNull
    private String password;

}
