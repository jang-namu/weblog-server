package com.bugflix.weblog.follow.controller;

import com.bugflix.weblog.follow.dto.FollowRequest;
import com.bugflix.weblog.follow.service.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Tag(name = "Follow API", description = "Follow 관련 API")
public class FollowController {
    private final FollowServiceImpl followService;

    @PostMapping("/v1/follows")
    @Operation(summary = "following 추가", description = "다른 사용자를 following 목록에 추가합니다.")
    public ResponseEntity<Void> addFollow(@Valid @RequestBody FollowRequest followRequest,
                                          @AuthenticationPrincipal UserDetails userDetails){
        followService.addFollow(followRequest,userDetails);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/follows/following")
    @Operation(summary = "following 삭제", description = "following 중인 사용자를 목록에서 삭제")
    public ResponseEntity<Void> deleteFollowing(@Valid @RequestBody FollowRequest followRequest,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        followService.deleteFollow(followRequest,userDetails,true);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/follows/follower")
    @Operation(summary = "follower 삭제", description = "사용자를 following하는 follower를 목록에서 삭제")
    public ResponseEntity<Void> deleteFollower(@Valid @RequestBody FollowRequest followRequest,
                                               @AuthenticationPrincipal UserDetails userDetails){
        followService.deleteFollow(followRequest,userDetails,false);

        return ResponseEntity.ok().build();
    }
}
