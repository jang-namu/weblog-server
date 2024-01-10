package com.bugflix.weblog.user.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpRequest {

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    private LocalDate birthDate;

}
