package com.bugflix.weblog.security.oauth.service;

import com.bugflix.weblog.security.JwtProvider;
import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.security.oauth.common.OAuthProvider;
import com.bugflix.weblog.security.oauth.repository.OAuthRepositoryComposite;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthService {
    private final OAuthRepositoryComposite oAuthRepositoryComposite;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public TokenResponse login(OAuthProvider oAuthProvider, String authCode) {

        User oauthMember = oAuthRepositoryComposite.fetch(oAuthProvider, authCode);
        User saved = saveOrUpdate(oauthMember);

        String accessToken = jwtProvider.createAccessToken(saved.getEmail(), saved.getRoles());
        String refreshToken = jwtProvider.createRefreshToken(saved.getEmail(), saved.getRoles());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(saved.getUserId())
                .nickname(saved.getNickname())
                .email(saved.getEmail())
                .build();
    }

    private User saveOrUpdate(User oauthMember) {
        Optional<User> user = userRepository.findByEmail(oauthMember.getEmail());
        if (user.isEmpty()) {
            return userRepository.save(oauthMember);
        }
        return user.get().update(oauthMember.getNickname(), oauthMember.getProfile().getImageUrl());
    }
}
