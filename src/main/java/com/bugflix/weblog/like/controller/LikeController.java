package com.bugflix.weblog.like.controller;

import com.bugflix.weblog.like.dto.LikeStatusResponse;
import com.bugflix.weblog.like.service.LikeServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    @PatchMapping("/v1/likes/{postId}")
    public ResponseEntity<LikeStatusResponse> changeLikeStatus(@PathVariable(name = "postId") Long postId,
                                                               @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId, userDetails));
    }
}
