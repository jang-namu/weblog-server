package com.bugflix.weblog.user.controller;

import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import com.bugflix.weblog.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User API", description = "사용자 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "회원가입", description = "신규 사용자 정보를 등록합니다.")
    @PostMapping("/v1/users")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴",
            description = "아이디(email)과 비밀번호 일치여부를 확인하고, 일치하다면 회원 정보를 삭제합니다.")
    @DeleteMapping("/v1/users")
    public ResponseEntity<Void> unregister(@RequestBody SignInRequest signInRequest) throws Exception {
        userService.unregister(signInRequest);
        return ResponseEntity.ok().build();
    }

}
