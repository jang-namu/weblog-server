package com.bugflix.weblog.profile.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
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
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    /**
     * 나의 Profile 반환
     */
    public ProfileResponse getMyProfile(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Profile profile = profileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Errors.PROFILE_NOT_FOUND));
        return ProfileResponse.of(user, profile);
    }

    public ProfileResponse getOthersProfile(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
        Profile profile = profileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Errors.PROFILE_NOT_FOUND));
        return ProfileResponse.of(user, profile);
    }
}
