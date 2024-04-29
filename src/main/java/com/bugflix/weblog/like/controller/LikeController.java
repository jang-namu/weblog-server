package com.bugflix.weblog.like.controller;

import com.bugflix.weblog.like.dto.LikeStatusResponse;
import com.bugflix.weblog.like.service.LikeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Like API", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    /***
     * 특정 Post의 좋아요 개수와 현재 사용자의 좋아요 여부를 반환합니다.
     *
     * @param postId 조회할 Post의 Id( Primary Key )
     * @param userDetails 조회를 요청한 user에 대한 정보;
     *                    현재 사용자가 특정 Post에 대한 좋아요 개수와 좋아요 여부를 조회할 권한이 있는지 확인하기 위해 필요합니다.
     * @return 특정 Post의 좋아요 개수와 현재 사용자의 좋아요 여부를 반환합니다.
     */
    @PatchMapping("/v1/likes/{postId}")
    @Operation(summary = "좋아요 정보 반환", description = "Post의 좋아요 개수, 나의 좋아요 여부 반환")
    public ResponseEntity<LikeStatusResponse> changeLikeStatus(@PathVariable Long postId,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId, userDetails));
    }
}
