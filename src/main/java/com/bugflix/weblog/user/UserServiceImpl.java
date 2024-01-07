package com.bugflix.weblog.user;

import com.bugflix.weblog.security.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void register(SignUpRequest signUpRequest) {
        Authority at = new Authority("ROLE_USER");
        User user = User.builder()
                .loginId(signUpRequest.getLoginId())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .build();

        at.assignUser(user);
        user.assignRoles(Collections.singletonList(at));

        userRepository.save(user);
    }

    public void unregister(SignInRequest signInRequest) throws Exception {
        User user = userRepository.findByLoginId(signInRequest.getLoginId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));

        // 비밀번호 검증 로직 필요
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new Exception();
        }
        userRepository.delete(user);
    }
}
