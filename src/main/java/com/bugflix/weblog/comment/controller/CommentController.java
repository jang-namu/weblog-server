package com.bugflix.weblog.comment.controller;

import com.bugflix.weblog.comment.dto.CommentRequest;
import com.bugflix.weblog.comment.dto.CommentResponse;
import com.bugflix.weblog.comment.service.CommentServiceImpl;
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

@Slf4j
@Tag(name = "Comment API", description = "댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentServiceImpl commentService;

    @Operation(summary = "댓글 저장", description = "사용자가 작성한 댓글 저장")
    @PostMapping("/v1/comments")
    public ResponseEntity<Void> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                            @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.saveComment(commentRequest, userDetails);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 삭제", description = "사용자가 지정한 자신의 댓글 삭제")
    @DeleteMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.deleteComment(commentId, userDetails);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 수정", description = "사용자가 자신의 댓글 수정")
    @PatchMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> modifyComment(@RequestParam String content,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.updateComment(commentId, content, userDetails);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "댓글 조회(URL)", description = "URL에 작성된 댓글 조회 ")
    @GetMapping("/v1/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByUrl(@RequestParam String pageUrl) {
        return ResponseEntity.ok(commentService.getCommentsByUrl(pageUrl));
    }

    @Operation(summary = "댓글 조회(postId)", description = "포스트에 달린 댓글 조회 ")
    @GetMapping("/v1/comments/{postId}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @Operation(summary = "댓글 조회(postId) Ver.2", description = "포스트에 달린 댓글 조회(Paging)")
    @GetMapping("/v2/comments/{postId}")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId,
                                                     @RequestParam Integer offset,
                                                     @RequestParam Integer limit) {
        return commentService.getCommentsByPostIdWithPaging(postId, offset, limit);
    }
}
