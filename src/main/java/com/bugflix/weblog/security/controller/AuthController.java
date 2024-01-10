package com.bugflix.weblog.security.controller;

import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.security.service.AuthServiceImpl;
import com.bugflix.weblog.user.dto.SignInRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API", description = "로그인 및 사용자 인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl authService;


    @Operation(summary = "로그인", description = "로그인 성공 시 액세스 토큰 및 리프레쉬 토큰을 발급합니다.")
    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(authService.login(signInRequest));
    }

    @Operation(summary = "액세스 토큰 재발급", description = "리프레쉬 토큰을 통해 만료된 액세스 토큰을 재발급합니다.")
    @PostMapping(value = "/reissue")
    public ResponseEntity<TokenResponse> refreshToken(
            HttpServletRequest httpServletRequest,
            @RequestBody SignInRequest signInRequest) throws Exception {
        return ResponseEntity.ok().body(
                TokenResponse.builder()
                        .accessToken(authService.refresh(httpServletRequest, signInRequest))
                        .build());
    }

    @Operation(summary = "JWT-기반 auth 테스트", description = "액세스 토큰의 유효성 테스트합니다.")
    @GetMapping(value = "/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }

}
