package com.bugflix.weblog.like.controller;

import com.bugflix.weblog.like.dto.LikeStatusResponse;
import com.bugflix.weblog.like.service.LikeServiceImpl;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;

    @GetMapping("/v1/likes/{postId}")
    public ResponseEntity<LikeStatusResponse> changeLikeStatus(@PathVariable(name = "postId") Long postId,
                                                               @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId, userDetails));
    }
}
