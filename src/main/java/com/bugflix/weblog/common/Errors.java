package com.bugflix.weblog.common;

public enum Errors {
    POST_NOT_FOUND(0, "포스트를 찾을 수 없습니다."),
    PARENT_COMMENT_NOT_FOUND(1, "부모 댓글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(2,"댓글을 찾을 수 없습니다" ),
    IS_NOT_MINE(3, "현재 사용자가 작성자가 아닙니다."),
    USER_NOT_FOUND(4, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(5, "비밀번호가 일치하지 않습니다."),
    EXPIRED_JWT(6, "만료된 JWT 입니다."),
    TOKEN_NOT_FOUND(7,"토큰을 찾을 수 없습니다."),
    INVALID_TOKEN(8, "유효하지 않은 토큰 입니다."),
    NOT_SUPPORTED_LOGIN_TYPE(9, "지원하지 않는 소셜 로그인 타입입니다."),
    TOKEN_EXCHANGE(10, "구글 서버 토큰 교환 실패"),
    FETCH_SOCIAL_RESOURCE(11, "소셜 로그인 리소스 조회 실패"),
    PROFILE_NOT_FOUND(12, "프로필을 찾을 수 없습니다.");

    private final int code;
    private final String description;

    Errors(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return code + ": " + description;
    }
}
