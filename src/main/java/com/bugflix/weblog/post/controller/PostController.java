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

    /***
     * 사용자가 작성한 Post를 저장합니다.
     *
     * @param postRequest 사용자가 저장을 요청한 Post;
     *                    제목( title ), 본문( content ), 주소( url )는 반드시 포함해야 합니다.
     * @param userDetails 저장을 요청한 사용자에 대한 정보;
     * @return 성공 코드 ( 요청을 성공적으로 처리한 경우 )
     */
    @PostMapping("/v1/posts")
    @Operation(summary = "Post 저장", description = "사용자가 작성한 Post를 저장합니다.")
    public ResponseEntity<Void> savePost(@Valid @RequestBody PostRequest postRequest,
                                         @AuthenticationPrincipal UserDetails userDetails) {
        postServiceImpl.savePost(postRequest, userDetails);
        return ResponseEntity.ok().build();
    }


    /***
     * 사용자가 본인의 Post를 업데이트합니다.
     *
     * @param postRequest 사용자가 업데이트 요청한 Post;
     *                    제목( title ), 본문( content ), 주소( url )는 반드시 포함해야 합니다.
     * @param postId 사용자가 업데이트할 기존 Post의 Id( Primary Key )
     * @return 성공 코드 ( 요청을 성공적으로 처리한 경우 )
     */
    @PutMapping("/v1/posts")
    @Operation(summary = "Post 수정", description = "사용자가 자신이 작성한 Post를 수정하여 저장합니다.")
    public ResponseEntity<Void> updatePost(@Valid @RequestBody PostRequest postRequest,
                                           @RequestParam Long postId) {
        postServiceImpl.updatePost(postRequest, postId);
        return ResponseEntity.ok().build();
    }

    /***
     * 사용자가 지정한 하나의 Post를 조회합니다.
     *
     * @param postId 조회할 Post의 Id( Primary Key )
     * @param userDetails 조회를 요청한 사용자의 정보;
     *                    Post에 대한 조회 권한이 있는지 확인하기 위해 필요합니다.
     * @return 사용자가 지정한 하나의 Post
     */
    @GetMapping("/v1/posts/{postId}")
    @Operation(summary = "Post 조회", description = "사용자가 지정한 하나의 Post를 반환합니다.")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.ok(postServiceImpl.getPost(postId));
        return ResponseEntity.ok(postServiceImpl.getPost(postId, userDetails));
    }

    /***
     * 특정 Page내의 전체 Post를 조회합니다.
     *
     * @param url Post를 조회할 Page의 Url
     * @return 특정 Page내의 전체 Post List
     */
    @GetMapping("/v1/posts")
    @Operation(summary = "Post 조회", description = "Page내의 전체 Post를 반환합니다.")
    public ResponseEntity<List<PostResponse>> getAllPost(@RequestParam String url) {

        return ResponseEntity.ok(postServiceImpl.getPosts(url));
    }

    /**
     * 특정 Post의 미리보기를 조회합니다.
     *
     * @param postId
     * @param userDetails
     * @return 특정 Post 미리보기
     */
    @GetMapping("/v1/posts/{postId}/preview")
    @Operation(summary = "단일 Post 미리보기 조회", description = "Post의 미리보기를 반환합니다.")
    public ResponseEntity<PostPreviewResponse> getPostPreview(@PathVariable Long postId,
                                                              @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(postServiceImpl.getPostPreview(postId, userDetails));
    }

    /***
     * 특정 Page 내의 Post 미리보기 전체를 조회합니다.
     *
     * @param url Post 미리보기를 조회할 Page의 Url
     * @param userDetails 조회를 요청한 사용자의 정보;
     *                    사용자에게 특정 Page 내의 Post 미리보기 조회 권한이 있는지 검사하기 위해 필요합니다.
     * @return 특정 Page 내의 Post 미리보기 전체 List
     */
    @GetMapping("/v1/posts/preview")
    @Operation(summary = "Post 미리보기 조회", description = "Page내의 Post 미리보기 전체를 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getPostPreview(@RequestParam String url,
                                                                    @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.ok(postServiceImpl.getPostPreviews(url));
        return ResponseEntity.ok(postServiceImpl.getPostPreviews(url, userDetails));
    }


    /***
     * 특정 Page에서 현재 사용자의 Post 미리보기를 조회합니다.
     *
     * @param url 특정 Page의 url
     * @param userDetails  조회를 요청한 사용자의 정보;
     * @return 특정 Page에서 현재 사용자의 Post 미리보기 전체 List
     */
    @GetMapping("/v1/posts/mine")
    @Operation(summary = "내 Post 미리보기 목록 조회 Ver.1", description = "url을 포함하는 경우 해당 주소에 작성된 포스트 내용을, " +
                                                                  "포함되어 있지 않은 경우에는 내가 작성한 포스트 목록을 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getMyPostPreview(@RequestParam(name = "url", required = false) String url,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        if (url == null) return ResponseEntity.ok(postServiceImpl.getMyPostPreview(userDetails));
        return ResponseEntity.ok(postServiceImpl.getMyPostPreview(url, userDetails));
    }

    /**
     * (페이징) 특정 Page에서 현재 사용자의 Post 미리보기를 조회합니다.
     *
     * @param url 특정 Page의 url
     * @param offset 조회 시작 인덱스
     * @param limit 한 번에 조회할 갯수
     * @param userDetails  조회를 요청한 사용자의 정보;
     * @return 특정 Page에서 현재 사용자의 Post 미리보기 전체 List
     */
    @GetMapping("/v2/posts/mine")
    @Operation(summary = "내 Post 미리보기 목록 조회 Ver.2", description = "url을 포함하는 경우 해당 주소에 작성된 포스트 내용을, " +
                                                                  "포함되어 있지 않은 경우에는 내가 작성한 포스트 목록을 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getMyPostPreviewWithPaging(@RequestParam(name = "url", required = false) String url,
                                                                      @RequestParam Integer offset,
                                                                      @RequestParam Integer limit,
                                                                      @AuthenticationPrincipal UserDetails userDetails) {
        if (url == null) return ResponseEntity.ok(postServiceImpl.getMyPostPreviewWithPaging(userDetails, offset, limit));
        return ResponseEntity.ok(postServiceImpl.getMyPostPreviewWithPaging(url, userDetails, offset, limit));
    }

    /**
     * (페이징) 다른 사용자의 Post 미리보기를 조회합니다.
     *
     * @param offset 조회 시작 인덱스
     * @param limit 한 번에 조회할 갯수
     * @param nickname 검색할 사용자 이름
     * @return 다른 사용자의 Post 미리보기 전체 List
     */
    @GetMapping("/v2/posts/users/{nickname}")
    @Operation(summary = "다른 사람의 Post 미리보기 목록 조회", description = "다른 사람이 작성한 포스트 목록을 반환합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getOthersPostPreviewWithPaging(@RequestParam Integer offset,
                                                                                @RequestParam Integer limit,
                                                                                @PathVariable String nickname) {
        return ResponseEntity.ok(postServiceImpl.getOthersPostPreviewWithPaging(nickname, offset, limit));
    }

    /***
     * 지정한 Post를 삭제합니다.
     *
     * @param postId 지정한 Post의 Id ( Primary Key )
     * @param userDetails Post삭제를 요청한 사용자 정보;
     *                    지정한 Post를 삭제할 권한이 있는 사용자인지 확인하기 위해 필요합니다.
     * @return 성공 코드 ( 삭제 성공 시 )
     */
    @DeleteMapping("/v1/posts/{postId}")
    @Operation(summary = "Post 삭제", description = "사용자가 자신의 Post를 삭제합니다.")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        postServiceImpl.deletePost(postId, userDetails);
        return ResponseEntity.ok().build();
    }

    /***
     * 인기 Post를 조회합니다.
     *
     * @param postPopularRequest  인기 Post를 조회기준; 조회 방식, 조회 시작 인덱스, 한 번에 조회할 Post 수를 Field로 가집니다.
     * @return Request 조건에 일치하는 Post 미리보기 List
     */
    @GetMapping("/v1/posts/ranks")
    @Operation(summary = "인기 Post 미리보기 조회", description = "Dashboard Main Page에 들어갈 주간/월간/연간 인기포스트를 조회합니다.")
    public ResponseEntity<List<PostPreviewResponse>> getPopularPosts(@Valid @ModelAttribute PostPopularRequest postPopularRequest) {
        return ResponseEntity.ok(postServiceImpl.getPopularPosts(postPopularRequest));
    }

    /***
     * 검색 결과에 포함된 Post의 미리보기 List를 반환합니다.
     *
     * @param postSearchRequest Post 검색 요청; maximum number of books, 검색 타입, 조회 시작 인덱스, 한 번에 조회할 Post 수를 Field로 가집니다.
     * @return Request 조건에 일치하는 Post 미리보기 List
     */
    @GetMapping("/v1/search/posts")
    @Operation(summary = "Post 검색", description = "태그+내용, 내용, 태그 중 하나를 선택하여 Post를 검색합니다.")
    public ResponseEntity<List<PostSearchResponse>> searchPost(@Valid @ModelAttribute PostSearchRequest postSearchRequest) {
        return ResponseEntity.ok(postServiceImpl.searchPost(postSearchRequest));
    }
}
