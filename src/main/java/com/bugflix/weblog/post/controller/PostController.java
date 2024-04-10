package com.bugflix.weblog.post.controller;

import com.bugflix.weblog.post.dto.PostPopularRequest;
import com.bugflix.weblog.post.dto.PostPreviewResponse;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.post.dto.PostResponse;
import com.bugflix.weblog.post.dto.*;
import com.bugflix.weblog.post.service.PostServiceImpl;
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
@Tag(name = "Post API", description = "Post 관련 API")
public class PostController {
    private final PostServiceImpl postServiceImpl;

    /**
     * Name : savePost
     * Parameter :
     * - PostRequest postRequest : Frontend 의 저장 요청 Post
     * Return :
     * - ResponseEntity<Void> :
     * - Success : 200 ok
     * - Failed : error
     * <p>
     * Explanation :
     * - post 저장 요청 수신
     * - 요청 처리 결과 반환
     * <p>
     * Scenario :
     * - 1. extension 에서 저장 요청
     * - 2. weblog page 에서 저장 요청
     */
    @PostMapping("/v1/posts")
    @Operation(summary = "Post 저장", description = "사용자가 작성한 Post를 저장합니다.")
    public ResponseEntity<Void> savePost(@Valid @RequestBody PostRequest postRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        postServiceImpl.savePost(postRequest, userDetails);
        return ResponseEntity.ok().build();
    }

    /**
     * Name : updatePost
     * Parameter :
     * - PostRequest postRequest : Frontend 의 수정 요청 Post
     * Return :
     * - ResponseEntity<Void> :
     * - Success : 200 ok
     * - Failed : error
     * <p>
     * Explanation :
     * - post 수정 요청 수신
     * - 요청 처리 결과 반환
     * <p>
     * Scenario :
     * - 1. extension 에서 수정 요청
     * - 2. weblog page 에서 수정 요청
     */
    @PutMapping("/v1/posts")
    @Operation(summary = "Post 수정", description = "사용자가 자신이 작성한 Post를 수정하여 저장합니다.")
    public ResponseEntity<Void> updatePost(@Valid @RequestBody PostRequest postRequest, @RequestParam(name = "postId") Long postId) throws Exception {
        postServiceImpl.updatePost(postRequest, postId);
        return ResponseEntity.ok().build();
    }

    /**
     * Name : getPost
     * Parameter :
     * - Long postId : 확인 요청한 post
     * Return :
     * - ResponseEntity<Post> :
     * - Success : 200 ok && Post
     * - Failed : error
     * <p>
     * Explanation :
     * - 특정 post 에 대한 접근 요청 수신
     * - 응답 반환
     * <p>
     * Scenario :
     * - 1. post 목록에서 post Click
     * - 2. post_id 로 post 검색
     */
    @GetMapping("/v1/posts/{postId}")
    @Operation(summary = "Post 조회", description = "사용자가 지정한 하나의 Post를 반환합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId,
                                                @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        if (userDetails == null) return ResponseEntity.ok(postServiceImpl.getPost(postId));
        return ResponseEntity.ok(postServiceImpl.getPost(postId, userDetails));
    }

    /**
     * Name : getAllPost
     * Parameter :
     * - String url: web page 주소
     * Return :
     * - ResponseEntity<ArrayList<Post>> :
     * - Success : 200 ok && ArrayList<Post>
     * - Failed : error
     * <p>
     * Explanation :
     * - 특정 web page 에 있는 모든 post 의 모든 post 요청
     * - 응답 반환
     * <p>
     * Scenario :
     * - 1. extension 에서 post 보기 요청
     */
    @GetMapping("/v1/posts")
    @Operation(summary = "Post 조회", description = "Page내의 전체 Post를 반환합니다.")
    public ResponseEntity<List<PostResponse>> getAllPost(@RequestParam(name = "url") String url) {

        return ResponseEntity.ok(postServiceImpl.getPosts(url));
    }

    /**
     * Name : getPostPreview
     * Parameter :
     * - String url: web page 주소
     * Return :
     * - ResponseEntity<ArrayList<PostPreviewResponse>> :
     * - Success : 200 ok && ArrayList<Post>
     * - Failed : error
     * <p>
     * Explanation :
     * - 특정 web page 에 있는 모든 post 의 preview 반환
     * <p>
     * Scenario :
     * - 1. extension 에서 post 목록 요청
     */
    @GetMapping("/v1/posts/preview")
    @Operation(summary = "Post 미리보기 조회", description = "Page내의 Post 미리보기 전체를 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getPostPreview(@RequestParam(name = "url") String url,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.ok(postServiceImpl.getPostPreview(url));
        return ResponseEntity.ok(postServiceImpl.getPostPreview(url, userDetails));
    }

    /**
     * Name : getMyPostPreview
     * Parameter :
     * - String url
     * - UserDetail userDetail
     * Return :
     * - ResponseEntity<ArrayList<PostPreviewResponse>>
     * <p>
     * Explanation :
     * - 1. url이 입력되면 특정 웹페이지 내의 사용자 Post Preview를 전체 반환
     * - 2. 입력되지 않으면 사용자 Post Preview를 전체 반환
     */
    @GetMapping("/v1/posts/mine")
    @Operation(summary = "Post 미리보기 반환", description = "사용자의 Post 미리보기를 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getMyPostPreview(@RequestParam(name = "url", required = false) String url,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        List<PostPreviewResponse> postPreviewResponses;

        if (url == null) postPreviewResponses = postServiceImpl.getMyPostPreview(userDetails);
        else postPreviewResponses = postServiceImpl.getMyPostPreview(url, userDetails);

        return ResponseEntity.ok(postPreviewResponses);
    }

    /**
     * Name : deletePost
     * Parameter :
     * - Long postId
     * Return :
     * - ResponseEntity<Void>
     * <p>
     * Explanation :
     * - postId 로 Post 삭제
     */
    @DeleteMapping("/v1/posts/{postId}")
    @Operation(summary = "Post 삭제", description = "사용자가 자신의 Post를 삭제합니다.")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        postServiceImpl.deletePost(postId, userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/posts/ranks")
    public ResponseEntity<List<PostPreviewResponse>> getPopularPosts(@Valid @ModelAttribute PostPopularRequest postPopularRequest) {
        return ResponseEntity.ok(postServiceImpl.getPopularPosts(postPopularRequest));
    }

    @GetMapping("/v1/search/posts")
    public ResponseEntity<List<PostSearchResponse>> searchPost(@Valid @ModelAttribute PostSearchRequest postSearchRequest) {
        return ResponseEntity.ok(postServiceImpl.searchPost(postSearchRequest));
    }
}
