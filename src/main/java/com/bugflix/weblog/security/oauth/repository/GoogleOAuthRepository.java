package com.bugflix.weblog.security.oauth.repository;

import com.bugflix.weblog.security.oauth.client.GoogleOAuthClient;
import com.bugflix.weblog.security.oauth.common.OAuthProvider;
import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthMemberResponse;
import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthTokenResponse;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleOAuthRepository implements OAuthRepository {

    private final GoogleOAuthClient googleOAuthClient;

    @Override
    public User fetch(String code) {
        GoogleOAuthTokenResponse googleOAuthTokenResponse = googleOAuthClient.fetchToken(code);
        GoogleOAuthMemberResponse googleOAuthMemberResponse = googleOAuthClient.fetchMember(googleOAuthTokenResponse.getAccessToken());
        return googleOAuthMemberResponse.toUser();
    }

    @Override
    public OAuthProvider identifyOAuthProvider() {
        return OAuthProvider.GOOGLE;
    }
}

