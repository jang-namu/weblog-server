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

@Slf4j
@Tag(name = "Like API", description = "좋아요 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    @PatchMapping("/v1/likes/{postId}")
    @Operation(summary = "좋아요 정보 반환", description = "Post의 좋아요 개수, 나의 좋아요 여부 반환")
    public ResponseEntity<LikeStatusResponse> changeLikeStatus(@PathVariable Long postId,
                                                               @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId, userDetails));
    }
}
