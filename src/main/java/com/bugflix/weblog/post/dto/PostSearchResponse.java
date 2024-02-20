package com.bugflix.weblog.post.dto;


import com.bugflix.weblog.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PostSearchResponse {
    private Long postId;
    private String title;
    private String nickname;
    private List<String> tags;

    @Builder(access = AccessLevel.PROTECTED)
    private PostSearchResponse(Long postId, String title, String nickname, List<String> tags) {
        this.postId = postId;
        this.title = title;
        this.nickname = nickname;
        this.tags = tags;
    }

    public static PostSearchResponse of(Post e, String nickname, List<String> tags) {
        return PostSearchResponse.builder()
                .postId(e.getPostId())
                .title(e.getTitle())
                .nickname(nickname)
                .tags(tags)
                .build();
    }
}
