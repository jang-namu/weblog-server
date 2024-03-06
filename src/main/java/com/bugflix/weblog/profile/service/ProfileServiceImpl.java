package com.bugflix.weblog.profile.service;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.profile.dto.ProfileResponse;
import com.bugflix.weblog.profile.repository.ProfileRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    /**
     * 나의 Profile 반환
     */
    public ProfileResponse getMyProfile(UserDetails userDetails) {
        // 1. userId로 Profile 검색
        User user = ((CustomUserDetails) userDetails).getUser();

        Profile profile = profileRepository.findByUserUserId(user.getUserId());

        return ProfileResponse.of(user, profile);

    }
}
