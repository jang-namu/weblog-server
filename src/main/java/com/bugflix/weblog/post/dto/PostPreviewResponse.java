package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostPreviewResponse {
    private Long postId;
    private String title;
    private String nickname;
    private List<String> tags;
    private Long likeCount;
    private Boolean isLiked;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    @Builder(access = AccessLevel.PRIVATE)
    private PostPreviewResponse(Long postId, String title, String nickname, List<String> tags,
                                Long likeCount, Boolean isLiked, String imageUrl,
                                LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.postId = postId;
        this.title = title;
        this.nickname = nickname;
        this.tags = tags;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public PostPreviewResponse(
            Post post,
            List<String> tags,
            String nickname,
            Boolean isLiked,
            LocalDateTime createdDate,
            LocalDateTime modifiedDate,
            Long likeCount) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageUrl();
        this.tags = tags;
        this.nickname = nickname;
        this.isLiked = isLiked;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.likeCount = likeCount;
    }

    public static PostPreviewResponse of(Post post, List<String> tags, String nickname, Boolean isLiked) {
        return PostPreviewResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .tags(tags)
                .nickname(nickname)
                .isLiked(isLiked)
                .createdDate(post.getCreatedDate())
                .likeCount(post.getLikeCount())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

}
