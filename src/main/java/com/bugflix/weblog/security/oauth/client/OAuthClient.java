package com.bugflix.weblog.security.oauth.client;

import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthMemberResponse;
import com.bugflix.weblog.security.oauth.dto.response.GoogleOAuthTokenResponse;

public interface OAuthClient {
    GoogleOAuthTokenResponse fetchToken(String code);

    GoogleOAuthMemberResponse fetchMember(String token);
}
