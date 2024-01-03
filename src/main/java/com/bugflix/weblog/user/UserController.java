package com.bugflix.weblog.user;

import com.bugflix.weblog.security.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping()
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        userService.register(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> unregister(@RequestBody SignInRequest signInRequest) throws Exception {
        userService.unregister(signInRequest);
        return ResponseEntity.ok().build();
    }


}
