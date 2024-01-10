package com.bugflix.weblog.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Schema(description = "회원가입 요청 DTO",
        requiredProperties = {"email", "password", "nickname", "phoneNumber", "birthDate"})
@Getter
public class SignUpRequest {

    @Schema(description = "이메일(로그인 ID)", example="bugflix19@example.com")
    private String email;

    @Schema(description = "비밀번호", example="test1234!")
    private String password;

    @Schema(description = "별명", example="namu")
    private String nickname;

    @Schema(description = "전화번호", example="01012341234")
    private String phoneNumber;

    @Schema(description = "생년월일", example="2000-01-01")
    private LocalDate birthDate;

}
