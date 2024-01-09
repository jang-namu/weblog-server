package com.bugflix.weblog.user.controller;

import com.bugflix.weblog.user.service.UserServiceImpl;
import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "User API Endpoint")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping()
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("회원가입");
        userService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> unregister(@RequestBody SignInRequest signInRequest) throws Exception {
        userService.unregister(signInRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.ok().build();
    }


}
