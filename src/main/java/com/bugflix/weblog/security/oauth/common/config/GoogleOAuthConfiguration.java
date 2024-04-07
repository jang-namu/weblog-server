package com.bugflix.weblog.security.oauth.common.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
public class GoogleOAuthConfiguration {
    private final String redirectUri;
    private final String clientId;
    private final String clientSecret;
    private final String[] scope;
    private final String tokenUri;
    private final String userInfoUri;

    public GoogleOAuthConfiguration(String redirectUri, String clientId, String clientSecret, String[] scope, String tokenUri, String userInfoUri) {
        this.redirectUri = redirectUri;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scope = scope;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
    }
}
