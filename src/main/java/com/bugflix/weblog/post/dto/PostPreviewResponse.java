package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Post 미리보기 응답 DTO")
@Getter
public class PostPreviewResponse {
    @Schema(description = "고유 식별자", example = "0")
    private Long postId;
    @Schema(description = "제목", example = "namu's title")
    private String title;
    @Schema(description = "소유자의 nickname", example = "namu")
    private String nickname;
    @Schema(description = "tag 목록", example = "[ tag1, tag2 ]")
    private List<String> tags;
    @Schema(description = "좋아요 개수", example = "123")
    private Long likeCount;
    @Schema(description = "현재 Login한 사용자의 좋아요 여부", example = "true")
    private Boolean isLiked;
    @Schema(description = "Post의 미리보기 Image Url", example = "http://~")
    private String imageUrl;
    @Schema(description = "생성일", example = "2024-05-05T06:45:54.306Z")
    private LocalDateTime createdDate;
    @Schema(description = "수정일", example = "2024-05-05T06:45:54.306Z")
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
