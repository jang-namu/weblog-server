package com.bugflix.weblog.security.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.ExpiredTokenException;
import com.bugflix.weblog.common.exception.InvalidTokenException;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.security.JwtProvider;
import com.bugflix.weblog.security.domain.RefreshToken;
import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.security.repository.RefreshTokenRepository;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public TokenResponse login(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new BadCredentialsException(Errors.USER_NOT_FOUND.getDescription()));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(Errors.INVALID_PASSWORD.getDescription());
        }

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRoles());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail(), user.getRoles());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .build();
    }

    public String refresh(HttpServletRequest httpServletRequest, SignInRequest signInRequest) {
        String authorizationHeader = jwtProvider.resolveToken(httpServletRequest);
        if (!jwtProvider.validateRefreshToken(authorizationHeader)) {
            throw new ExpiredTokenException(Errors.EXPIRED_JWT);
        }
        RefreshToken refreshToken = refreshTokenRepository.findById(signInRequest.getEmail()).
                orElseThrow(() -> new ResourceNotFoundException(Errors.TOKEN_NOT_FOUND));
        if (refreshToken.getRefreshToken().equals(authorizationHeader)) {
            User user = userRepository.findByEmail(signInRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                return jwtProvider.createAccessToken(user.getEmail(), user.getRoles());
            }
        }
        throw new InvalidTokenException(Errors.INVALID_TOKEN);
    }

}
