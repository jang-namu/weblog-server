package com.bugflix.weblog.security;

import com.bugflix.weblog.security.dto.SignInRequest;
import com.bugflix.weblog.security.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")  // UserController와 endpoint 통일
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl userAccountService;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok().body(userAccountService.login(signInRequest));
    }

}
