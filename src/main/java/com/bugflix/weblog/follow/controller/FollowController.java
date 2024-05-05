package com.bugflix.weblog.follow.controller;

import com.bugflix.weblog.follow.dto.FollowRequest;
import com.bugflix.weblog.follow.dto.FollowResponse;
import com.bugflix.weblog.follow.service.FollowServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@Tag(name = "Follow API", description = "Follow 관련 API")
public class FollowController {
    private final FollowServiceImpl followService;

    /**
     * 대상 사용자를 팔로잉 목록에 추가
     *
     * @param followRequest 팔로잉할 대상 사용자의 nickname
     * @param userDetails 팔로우 요청한 사용자에 대한 정보;
     * @return 성공 코드 ( 요청을 성공적으로 처리한 경우 )
     */
    @PostMapping("/v1/follows")
    @Operation(summary = "following 추가", description = "다른 사용자를 following 목록에 추가합니다.")
    public ResponseEntity<Void> addFollow(@Valid @RequestBody FollowRequest followRequest,
                                          @AuthenticationPrincipal UserDetails userDetails){
        followService.addFollow(followRequest,userDetails);

        return ResponseEntity.ok().build();
    }

    /**
     * 현재 사용자를 팔로우하는 사용자 리스트 조회
     *
     * @param userDetails 팔로우 조회 요청한 사용자에 대한 정보;
     * @return 팔로워 목록 List
     */
    @GetMapping("/v1/follows/mine/follower")
    @Operation(summary = "follower 조회", description = "현재 사용자를 follow하는 follower 목록을 조회합니다.")
    public ResponseEntity<List<FollowResponse.MyFollowResponse>> searchFollowing(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(followService.searchFollowers(userDetails));
    }

    /**
     * 현재 사용자가 팔로우하는 사용자 리스트 조회
     *
     * @param userDetails 팔로잉 조회 요청한 사용자에 대한 정보;
     * @return 팔로잉 목록 List
     */
    @GetMapping("/v1/follows/mine/following")
    @Operation(summary = "following 조회", description = "현재 사용자가 following 중인 사용자 목록을 조회합니다.")
    public ResponseEntity<List<FollowResponse>> searchFollower(@AuthenticationPrincipal UserDetails userDetails){

        return ResponseEntity.ok(followService.searchFollwings(userDetails));
    }

    /**
     * 지정한 사용자를 팔로우하는 사용자 리스트 조회
     * @param nickname
     * @return 팔로워 목록 List
     */
    @GetMapping("/v1/follows/{nickname}/follower")
    @Operation(summary = "follower 조회", description = "현재 사용자를 follow하는 follower 목록을 조회합니다.")
    public ResponseEntity<List<FollowResponse>> searchFollowing(@PathVariable String nickname) {

        return ResponseEntity.ok(followService.searchFollowers(nickname));
    }

    /**
     * 지정한 사용자가 팔로우하는 사용자 리스트 조회
     * @param nickname
     * @return 팔로잉 목록 List
     */
    @GetMapping("/v1/follows/{nickname}/following")
    @Operation(summary = "following 조회", description = "현재 사용자가 following 중인 사용자 목록을 조회합니다.")
    public ResponseEntity<List<FollowResponse>> searchFollower(@PathVariable String nickname){

        return ResponseEntity.ok(followService.searchFollwings(nickname));
    }

    /**
     * 사용자 팔로워 목록 중 한 명 삭제
     *
     * @param followRequest 삭제할 사용자 ninkname
     * @param userDetails 팔로우 삭제를 요청한 사용자에 대한 정보;
     * @return 성공 코드 ( 요청을 성공적으로 처리한 경우 )
     */
    @DeleteMapping("/v1/follows/follower")
    @Operation(summary = "follower 삭제", description = "사용자를 following하는 follower를 목록에서 삭제")
    public ResponseEntity<Void> deleteFollower(@Valid @RequestBody FollowRequest followRequest,
                                               @AuthenticationPrincipal UserDetails userDetails){
        followService.deleteFollower(followRequest,userDetails);

        return ResponseEntity.ok().build();
    }

    /**
     * 사용자가 팔로잉하는 목록 중 한 명 삭제
     *
     * @param followRequest 삭제할 사용자 ninkname
     * @param userDetails 팔로잉 삭제를 요청한 사용자에 대한 정보;
     * @return 성공 코드 ( 요청을 성공적으로 처리한 경우 )
     */
    @DeleteMapping("/v1/follows/following")
    @Operation(summary = "following 삭제", description = "following 중인 사용자를 목록에서 삭제")
    public ResponseEntity<Void> deleteFollowing(@Valid @RequestBody FollowRequest followRequest,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        followService.deleteFollowing(followRequest,userDetails);

        return ResponseEntity.ok().build();
    }

}
