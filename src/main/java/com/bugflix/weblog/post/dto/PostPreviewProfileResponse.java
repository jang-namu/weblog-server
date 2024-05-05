package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Post 미리보기, 프로필 응답 DTO")
@Getter
public class PostPreviewProfileResponse {
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
    @Schema(description = "작성자의 프로필 이미지", example = "http://~")
    private String profileImageUrl;
    @Schema(description = "생성일", example = "2024-05-05T06:45:54.306Z")
    private LocalDateTime createdDate;
    @Schema(description = "수정일", example = "2024-05-05T06:45:54.306Z")
    private LocalDateTime modifiedDate;

    @Builder(access = AccessLevel.PRIVATE)
    private PostPreviewProfileResponse(Long postId, String title, String nickname, List<String> tags, Long likeCount, Boolean isLiked, String imageUrl, String profileImageUrl, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.postId = postId;
        this.title = title;
        this.nickname = nickname;
        this.tags = tags;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
        this.profileImageUrl = profileImageUrl;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static PostPreviewProfileResponse of(Post post, User user, List<String> tags, Boolean isLiked) {
        return PostPreviewProfileResponse.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .imageUrl(post.getImageUrl())
                .tags(tags)
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfile().getImageUrl())
                .isLiked(isLiked)
                .createdDate(post.getCreatedDate())
                .likeCount(post.getLikeCount())
                .modifiedDate(post.getModifiedDate())
                .build();
    }

}
