package com.bugflix.weblog.like;

import com.bugflix.weblog.like.dto.LikeStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
@Slf4j
public class LikeController {
    private final LikeServiceImpl likeServiceImpl;
    @GetMapping("/{postId}")
    public ResponseEntity<LikeStatus> changeLikeStatus(@PathVariable(name = "postId")Long postId){
        return ResponseEntity.ok(likeServiceImpl.changeLikeStatus(postId));
    }
}
