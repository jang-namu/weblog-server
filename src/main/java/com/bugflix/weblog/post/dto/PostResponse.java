package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.tag.dto.TagResponse;
import com.bugflix.weblog.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {
    private Long postId;
    private String title;
    private List<TagResponse> tags;
    private String content;
    private String memo;
    private Long likeCount;
    private boolean isLike;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Builder
    private PostResponse(Long postId, String title, List<TagResponse> tags, String content, String memo, Long likeCount, boolean isLike, String nickname, String profileImageUrl, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.postId = postId;
        this.title = title;
        this.tags = tags;
        this.content = content;
        this.memo = memo;
        this.likeCount = likeCount;
        this.isLike = isLike;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    @Builder
    private PostResponse(Long postId, String title, String content, String memo) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.memo = memo;
    }

    public static PostResponse of(Post post, User user, List<TagResponse> tags, Long likeCount, Boolean isLike) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .tags(tags)
                .content(post.getContent())
                .memo(post.getMemo())
                .likeCount(likeCount)
                .isLike(isLike)
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfile().getImageUrl())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getModifiedDate())
                .build();
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .memo(post.getMemo())
                .build();
    }
}
