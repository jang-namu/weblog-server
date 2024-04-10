package com.bugflix.weblog.security.oauth.repository;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.NotSupportedLoginTypeException;
import com.bugflix.weblog.security.oauth.common.OAuthProvider;
import com.bugflix.weblog.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class OAuthRepositoryComposite {

    private final Map<OAuthProvider, OAuthRepository> mapping;

    public OAuthRepositoryComposite(Set<OAuthRepository> oAuthRepositories) {
        this.mapping = oAuthRepositories.stream()
                .collect(toMap(OAuthRepository::identifyOAuthProvider, identity()));
    }

    public User fetch(OAuthProvider oAuthProvider, String authCode) {
        return getClient(oAuthProvider).fetch(authCode);
    }

    private OAuthRepository getClient(OAuthProvider oAuthProvider) {
        return Optional.ofNullable(mapping.get(oAuthProvider))
                .orElseThrow(() -> new NotSupportedLoginTypeException(Errors.NOT_SUPPORTED_LOGIN_TYPE));
    }
}
