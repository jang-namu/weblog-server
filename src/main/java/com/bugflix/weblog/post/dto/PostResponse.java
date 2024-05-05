package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Post 조회 응답 DTO")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostResponse {
    @Schema(description = "고유 식별자", example = "0")
    private Long postId;
    @Schema(description = "제목", example = "namu's title")
    private String title;
    @Schema(description = "tag 목록", example = "[ tag1, tag2 ]")
    private List<String> tags;
    @Schema(description = "본문", example = "namu's content")
    private String content;
    @Schema(description = "요약", example = "namu's memo")
    private String memo;
    @Schema(description = "좋아요 개수", example = "123")
    private Long likeCount;
    @Schema(description = "현재 Login한 사용자의 좋아요 여부", example = "true")
    private boolean isLike;
    @Schema(description = "소유자의 nickname", example = "namu")
    private String nickname;
    @Schema(description = "소유자의 profile image url", example = "http://~")
    private String profileImageUrl;
    @Schema(description = "생성일", example = "2024-05-05T06:45:54.306Z")
    private LocalDateTime createdDate;
    @Schema(description = "수정일", example = "2024-05-05T06:45:54.306Z")
    private LocalDateTime updatedDate;

    @Builder(access = AccessLevel.PRIVATE)
    private PostResponse(Long postId, String title, List<String> tags, String content, String memo, Long likeCount, boolean isLike, String nickname, String profileImageUrl, LocalDateTime createdDate, LocalDateTime updatedDate) {
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

    @Builder(access = AccessLevel.PRIVATE)
    private PostResponse(Long postId, String title, String content, String memo) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.memo = memo;
    }

    public static PostResponse of(Post post, User user, List<String> tags, Long likeCount, Boolean isLike) {
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
