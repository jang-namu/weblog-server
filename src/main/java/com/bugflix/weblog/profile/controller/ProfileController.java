package com.bugflix.weblog.profile.controller;

import com.bugflix.weblog.profile.dto.ProfileResponse;
import com.bugflix.weblog.profile.service.ProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Tag(name = "Profile API", description = "Profile 관련 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileServiceImpl profileService;
    @GetMapping("/v1/profiles/mine")
    @Operation(summary = "Profile 조회", description = "사용자 본인 Profile을 조회합니다.")
    public ResponseEntity<ProfileResponse> getMyProfile(UserDetails userDetails) throws Exception{

        return ResponseEntity.ok(profileService.getMyProfile(userDetails));
    }

}
