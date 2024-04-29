package com.bugflix.weblog.security.oauth.controller;

import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.security.oauth.common.OAuthProvider;
import com.bugflix.weblog.security.oauth.service.OAuthService;
import com.bugflix.weblog.security.service.AuthServiceImpl;
import com.bugflix.weblog.user.dto.SignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "로그인 및 사용자 인증 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;
    private final OAuthService oAuthService;

    @Operation(summary = "로그인", description = "로그인 성공 시 액세스 토큰 및 리프레쉬 토큰을 발급합니다.")
    @PostMapping(value = "/v1/auths/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(authService.login(signInRequest));
    }

    @Operation(summary = "액세스 토큰 재발급", description = "리프레쉬 토큰을 통해 만료된 액세스 토큰을 재발급합니다.")
    @PostMapping(value = "/v1/auths/reissue")
    public ResponseEntity<TokenResponse> refreshToken(
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(
                TokenResponse.builder()
                        .accessToken(authService.refresh(httpServletRequest, signInRequest))
                        .build());
    }

    @Operation(summary = "JWT-기반 auth 테스트", description = "액세스 토큰의 유효성 테스트합니다.")
    @GetMapping(value = "/v1/auths/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "OAuth 2.0 회원가입/로그인", description = "소셜 로그인, AuthorizationCode를 받아 회원가입/로그인 수행")
    @GetMapping("/login/oauth2/code/{oAuthProvider}")
    public ResponseEntity<TokenResponse> oAuthLogin(@PathVariable OAuthProvider oAuthProvider,
                                                    @RequestParam String code) {
        return ResponseEntity.ok().body(oAuthService.login(oAuthProvider, code));
    }
}
