package com.bugflix.weblog.security.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(Errors.USER_NOT_FOUND.getDescription()))
        );
    }
}
