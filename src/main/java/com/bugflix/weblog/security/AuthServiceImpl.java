package com.bugflix.weblog.security;

import com.bugflix.weblog.security.dto.SignInRequest;
import com.bugflix.weblog.security.dto.TokenResponse;
import com.bugflix.weblog.user.User;
import com.bugflix.weblog.user.UserRepository;
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

    public TokenResponse login(SignInRequest signInRequest) {

        User user = userRepository.findByLoginId(signInRequest.getLoginId()).orElseThrow(() ->
                new BadCredentialsException("계정정보가 존재하지 않습니다."));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("계정정보가 존재하지 않습니다.");
        }

        return TokenResponse.from(jwtProvider.createToken(user.getLoginId(), user.getRoles()));
    }

}
