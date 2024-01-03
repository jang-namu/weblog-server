package com.bugflix.weblog.user.dto;

public class SignUpRequest {

    private String loginId;

    private String password;

    private String nickname;

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }
}
