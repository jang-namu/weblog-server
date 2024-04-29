package com.bugflix.weblog.user.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.user.domain.Authority;
import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.dto.SignInRequest;
import com.bugflix.weblog.user.dto.SignUpRequest;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void register(SignUpRequest signUpRequest) {
        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .nickname(signUpRequest.getNickname())
                .build();

        Profile profile = new Profile(signUpRequest.getPhoneNumber(), signUpRequest.getBirthDate());
        profile.assignUser(user);

        Authority at = new Authority("ROLE_USER");
        at.assignUser(user);

        user.assignRoles(Collections.singletonList(at));
        user.assignProfile(profile);

        userRepository.save(user);
    }

    public void unregister(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new ResourceNotFoundException(Errors.USER_NOT_FOUND));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException(Errors.INVALID_PASSWORD.getDescription());
        }
        userRepository.delete(user);
    }

    public String findNicknameByPostId(Long postId) {
        User user = userRepository.findByPosts_PostId(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
        return user.getNickname();
    }

}
