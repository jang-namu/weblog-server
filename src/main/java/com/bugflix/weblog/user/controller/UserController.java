package com.bugflix.weblog.user.controller;

import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import com.bugflix.weblog.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    /***
     * 신규 사용자 정보를 등록합니다.
     *
     * @param signUpRequest 신규 등록을 요청한 사용자의 정보;
     * @return 성공적으로 처리한 경우 성공 코드를 반환합니다.
     */
    @Operation(summary = "회원가입", description = "신규 사용자 정보를 등록합니다.")
    @PostMapping("/v1/users")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        userService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    /***
     * Valid 검사를 통과한 회원 정보를 삭제합니다.
     *
     * @param signInRequest 계정 삭제를 요청한 사용자의 정보;
     *                      삭제 요청이 유효한지 검사하기 위해 Email과 Password를 입력받습니다.
     * @return 성공적으로 처리한 경우 성공 코드를 반환합니다.
     * Todo @throws Exception
     */
    @Operation(summary = "회원탈퇴",
            description = "아이디(email)과 비밀번호 일치여부를 확인하고, 일치하다면 회원 정보를 삭제합니다.")
    @DeleteMapping("/v1/users")
    public ResponseEntity<Void> unregister(@Valid @RequestBody SignInRequest signInRequest) throws Exception {
        userService.unregister(signInRequest);
        return ResponseEntity.ok().build();
    }

}
