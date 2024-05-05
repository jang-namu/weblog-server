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
     * 특정 Post에 대한 사용자의 좋아요 여부를 변경합니다
     *
     * @param postId 좋아요 상태를 변경할 Post의 식별자 ( id )
     * @param userDetails 좋아요 상태 변경을 요청한 사용자 정보
     * @return Post의 좋아요 수, 현재 로그인한 사용자의 좋아요 여부
     */

    @PatchMapping("/v1/likes/{postId}")
    @Operation(summary = "좋아요 상태 변경", description = "Post에 대한 사용자의 좋아요 여부를 변경하고 결과를 반환합니다.")
    public ResponseEntity<LikeStatusResponse> changeLikeStatus(@PathVariable Long postId,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId, userDetails));
    }
}
