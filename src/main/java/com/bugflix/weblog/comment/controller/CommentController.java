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

    /**
     * 사용자가 입력한 댓글을 저장합니다.
     *
     * @param commentRequest 사용자가 저장을 요청한 댓글. 댓글의 본문( Content )은 공백일 수 없습니다.
     * @param userDetails 댓글 저장을 요청한 user에 대한 정보;
     *                    댓글 저장을 요청한 user가 적절한 권한을 가진 사용자인지 확인하기 위해 필요합니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     * */
    @Operation(summary = "댓글 저장", description = "사용자가 작성한 댓글 저장")
    @PostMapping("/v1/comments")
    public ResponseEntity<Void> saveComment(@Valid @RequestBody CommentRequest commentRequest,
                                            @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.saveComment(commentRequest, userDetails);
        return ResponseEntity.ok().build();
    }
    /**
     * 사용자가 요청한 댓글을 삭제합니다.
     *
     * @param commentId 사용자가 지정한 댓글의 Id( Primary Key ).
     * @param userDetails 댓글 삭제를 요청한 user에 대한 정보;
     *                    댓글의 소유자와 삭제 요청자가 동일한지 확인하기 위해 필요합니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     * */
    @Operation(summary = "댓글 삭제", description = "사용자가 지정한 자신의 댓글 삭제")
    @DeleteMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.deleteComment(commentId, userDetails);
        return ResponseEntity.ok().build();
    }

    /***
     * 사용자가 요청한 댓글을 업데이트합니다.
     *
     * @param content 사용자가 업데이트 요청한 댓글의 본문.
     * @param commentId 사용자가 업데이트 요청한 댓글의 Id ( Primary Key ).
     * @param userDetails 댓글 업데이트를 요청한 user에 대한 정보;
     *                    댓글의 소유자와 업데이트 요청자가 동일한지 확인하기 위해 필요합니다.
     * @return 성공 코드( 사용자의 요청을 성공적으로 처리한 경우 )
     */
    @Operation(summary = "댓글 수정", description = "사용자가 자신의 댓글 수정")
    @PatchMapping("/v1/comments/{commentId}")
    public ResponseEntity<Void> modifyComment(@RequestParam String content,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        commentService.updateComment(commentId, content, userDetails);
        return ResponseEntity.ok().build();
    }

    /***
     * 특정 Page에 작성된 댓글 전체를 조회합니다.
     *
     * @param pageUrl 댓글 조회를 요청한 Page의 URL.
     * @return 특정 Page에 작성된 댓글 List
     */
    @Operation(summary = "댓글 조회(URL)", description = "URL에 작성된 댓글 조회 ")
    @GetMapping("/v1/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByUrl(@RequestParam String pageUrl) {
        return ResponseEntity.ok(commentService.getCommentsByUrl(pageUrl));
    }

    /***
     * 특정 Post에 작성된 댓글 전체를 조회합니다.
     *
     * @param postId 댓글 조회를 요청한 Post의 Id( Primary Key )
     * @return 특정 Post에 작성된 댓글 List
     */
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
