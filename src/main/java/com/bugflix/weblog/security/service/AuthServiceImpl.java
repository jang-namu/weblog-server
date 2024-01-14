package com.bugflix.weblog.security.service;

import com.bugflix.weblog.security.JwtProvider;
import com.bugflix.weblog.security.domain.RefreshToken;
import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.security.repository.RefreshTokenRepository;
import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenResponse login(SignInRequest signInRequest) {

        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new BadCredentialsException("계정정보가 존재하지 않습니다."));

        /*
        passwordEncoder로 encode를 수행할 때 마다 매 번 다른 값이 나온다.
        따라서, equals로는 인증을 수행할 수 없고 matches를 통해 확인해야 한다.
        */
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("계정정보가 존재하지 않습니다.");
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

    public String refresh(HttpServletRequest httpServletRequest, SignInRequest signInRequest) throws Exception {
        String authorizationHeader = jwtProvider.resolveToken(httpServletRequest);
        if (!jwtProvider.validateToken(authorizationHeader)) {
            throw new Exception("Refresh-Token 만료");
        }
        RefreshToken refreshToken = refreshTokenRepository.findById(signInRequest.getEmail()).
                orElseThrow(() -> new IllegalStateException("REFRESH_TOKEN을 찾을 수 없습니다."));
        if (refreshToken.getRefreshToken().equals(authorizationHeader)) {
            User user = userRepository.findByEmail(signInRequest.getEmail())
                    .orElseThrow(Exception::new);
            if (passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
                return jwtProvider.createAccessToken(user.getEmail(), user.getRoles());
            }
        }
        throw new Exception("Unauthorized");
    }

}
