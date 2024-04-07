package com.bugflix.weblog.security.oauth.repository;

import com.bugflix.weblog.security.oauth.common.OAuthProvider;
import com.bugflix.weblog.user.domain.User;

public interface OAuthRepository {
    User fetch(String code);

    OAuthProvider identifyOAuthProvider();
}
