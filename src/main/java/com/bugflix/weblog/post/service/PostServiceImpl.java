package com.bugflix.weblog.post.service;

import com.bugflix.weblog.like.service.LikeServiceImpl;
import com.bugflix.weblog.page.domain.Page;
import com.bugflix.weblog.page.repository.PageRepository;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.post.dto.PostPreviewResponse;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.post.dto.PostResponse;
import com.bugflix.weblog.post.repository.PostRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.tag.domain.Tag;
import com.bugflix.weblog.tag.repository.TagRepository;
import com.bugflix.weblog.tag.service.TagServiceImpl;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl {
    private final PostRepository postRepository;
    private final PageRepository pageRepository;
    private final LikeServiceImpl likeServiceImpl;
    private final TagServiceImpl tagService;
    private final UserServiceImpl userService;
    private final TagRepository tagRepository;


    /**
     * Name : savePost
     * Parameter :
     * - PostRequest postRequest : Frontend 의 저장 요청 Post
     * Return :
     * - void
     * <p>
     * Explanation :
     * - postRequest 의 url 정보로 Page Table 에 Entity 탐색
     * - (1) 동일한 url을 가지는 Page Entity 존재 여부 확인
     * - 존재 O : page와 post 관계 mapping
     * - 존재 X : Page Entity 존재하지 않으면, postRequest의 url으로 Page Entity 생성하여 저장 & post와 관계 mapping
     * - (2) Tag 와 연관관계 mapping 후 저장
     * <p>
     * - Todo Spring Security Context의 user 정보를 불러와 post와 관계 mapping
     */
    public void savePost(PostRequest postRequest, UserDetails userDetails) {
        String url = postRequest.getUrl();

        // Page
        Optional<Page> existingPage = pageRepository.findPageByUrl(url);
        Page page = existingPage.orElseGet(() -> {
            Page newPage = new Page(url);
            return pageRepository.save(newPage);
        });

        User user = ((CustomUserDetails)userDetails).getUser();

        // Post
        Post post = new Post(postRequest, user, page);
        Post savedPost = postRepository.save(post);

        // Tag
        List<String> tags = postRequest.getTags();
        for (String tagContent : tags) {
            Tag tag = new Tag(savedPost, tagContent);
            tagRepository.save(tag);
        }

    }

    /**
     * Name : updatePost
     * Parameter :
     * - PostRequest postRequest
     * - Long postId
     * Return :
     * - void
     * <p>
     * Explanation :
     * - 1. postId 가 존재하지 않으면 예외처리
     * - 2. postId 가 존재하면 사용자가 요청한 정보로 update하여 저장
     */
    public void updatePost(PostRequest postRequest, Long postId) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception(""));
        post.updateTitle(postRequest.getTitle());
        post.updateMemo(postRequest.getMemo());
        post.updateContent(postRequest.getContent());

        // Todo image_url 추가

        postRepository.save(post);
    }

    /**
     * Name : getPost
     * Parameter :
     * - Long postId
     * Return :
     * - PostResponse
     * <p>
     * Explanation :
     * - postId 로 Post Entity 탐색
     * - Post 존재하지 않으면 예외처리, 존재하면 출력
     */
    public PostResponse getPost(Long postId, UserDetails userDetails) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(() -> new Exception(""));
        PostResponse postResponse = new PostResponse(post);

        Long userId = ((CustomUserDetails)userDetails).getUser().getUserId();
        // Todo 1. post Update Logic 구성
        postResponse.setNickname(userService.findNicknameByPostId(postId));
        postResponse.setTags(tagService.findTagsByPostId(postId));
        postResponse.setLike(likeServiceImpl.isLiked(postId, userId));
        postResponse.setLikeCount(likeServiceImpl.countLikes(postId));

        return postResponse;
    }

    /**
     * Name : getPost
     * Parameter :
     * - Long postId
     * Return :
     * - PostResponse
     * <p>
     * Explanation :
     * - postId 로 Post Entity 탐색
     * - Post 존재하지 않으면 예외처리, 존재하면 출력
     */
    public List<PostResponse> getPosts(String url) {

        List<Post> resultList = postRepository.findByPageUrl(url);
        List<PostResponse> resultArrayList = new ArrayList<>();

        resultList.forEach(post -> resultArrayList.add(new PostResponse(post)));
        // Todo : Tag, LikeCount, Profile 등 추가 필요
        return resultArrayList;
    }

    /**
     * Name : getPostPreview
     * Parameter :
     * - String url
     * Return :
     * - ArrayList<PostPreviewResponse>
     * <p>
     * Explanation :
     * - 페이지 내의 모든 post 미리 보기 목록 반환
     */
    public List<PostPreviewResponse> getPostPreview(String url, UserDetails userDetails) {
        List<PostPreviewResponse> postPreviews = new ArrayList<>();

        List<Post> postList = postRepository.findByPageUrl(url);
        Long userId = ((CustomUserDetails)userDetails).getUser().getUserId();

        for(Post post : postList) {
            Long postId = post.getPostId();

            PostPreviewResponse postPreview = new PostPreviewResponse(
                    post,
                    tagRepository.findTagsByPostPostId(postId),
                    userService.findNicknameByPostId(postId),
                    likeServiceImpl.isLiked(postId, userId),
                    post.getCreatedDate(),
                    post.getModifiedDate(),
                    likeServiceImpl.countLikes(postId));

            // Todo postId 가 다른 여러 개의 Entity가 입력되었을 때, like Count 가 정상 작동하는지 확인.

            postPreviews.add(postPreview);  // List 에 postPreview Entity 추가
        }
        return postPreviews;
    }

    /**
     * Name : getMyPostPreview
     * Parameter :
     * - String url
     * Return :
     * - ArrayList<PostPreviewResponse>
     * <p>
     * Explanation :
     * - 페이지 내에서 내가 작성한 post 미리 보기 목록 반환
     */
    public List<PostPreviewResponse> getMyPostPreview(String url, UserDetails userDetails) {
        Long userId = ((CustomUserDetails)userDetails).getUser().getUserId();
        List<Post> posts = postRepository.findByPageUrlAndUserUserId(url, userId);
        List<PostPreviewResponse> postPreviews = new ArrayList<>();

        for (Post post : posts) {
            Long postId = post.getPostId();

            PostPreviewResponse postPreview = new PostPreviewResponse(
                    post,
                    tagRepository.findTagsByPostPostId(postId),
                    userService.findNicknameByPostId(postId),
                    likeServiceImpl.isLiked(postId, userId),
                    post.getCreatedDate(),
                    post.getModifiedDate(),
                    likeServiceImpl.countLikes(postId));

            postPreviews.add(postPreview);
        }
        return postPreviews;
    }

    /**
     * Name : deletePost
     * Parameter :
     * - Long postId
     * Return :
     * - void
     * <p>
     * Explanation :
     * - PostId 를 매개변수로 받아, Post Entity 검색
     * - 존재하면 delete
     * - 존재하지 않으면 IllegalArgumentException 예외 처리 ( Invalid postId )
     */
    public void deletePost(Long postId) throws IllegalArgumentException {
        // Todo Global Exception Handler 생성
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid Post Id"));
        postRepository.delete(post);
    }
}
