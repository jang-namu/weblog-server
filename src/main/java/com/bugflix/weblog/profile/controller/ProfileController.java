package com.bugflix.weblog.profile.controller;

import com.bugflix.weblog.profile.dto.ProfileResponse;
import com.bugflix.weblog.profile.service.ProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "Profile API", description = "Profile 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServiceImpl profileService;

    /***
     * 현재 사용자의 Profile을 조회합니다.
     *
     * @param userDetails Profile 조회를 요청한 user에 대한 정보;
     *                    조회를 요청한 user를 식별하고 Profile을 조회할 권한이 있는지 확인하기 위해 필요합니다.
     * @return 현재 사용자의 Profile
     */
    @GetMapping("/v1/profiles/mine")
    @Operation(summary = "마이프로필 조회", description = "사용자 본인 프로필을 조회합니다.")
    public ResponseEntity<ProfileResponse> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(profileService.getMyProfile(userDetails));
    }

    /***
     * 다른 사람의 Profile을 조회합니다.
     *
     * @param nickname 검색할 사용자 이름
     * @return 현재 사용자의 Profile
     */
    @GetMapping("/v1/profiles/users/{nickname}")
    @Operation(summary = "다른 사람 프로필 조회", description = "다른 사람의 프로필을 조회합니다.")
    public ResponseEntity<ProfileResponse> getOthersProfile(@PathVariable String nickname,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null)
            return ResponseEntity.ok(profileService.getOthersProfile(nickname));
        return ResponseEntity.ok(profileService.getOthersProfile(nickname, userDetails));
    }

}
