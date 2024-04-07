package com.bugflix.weblog.security.oauth.common;

public enum OAuthProvider {
    GOOGLE();

    public static OAuthProvider fromName(String type) {
        return OAuthProvider.valueOf(type.toUpperCase());
    }
}

