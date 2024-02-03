package com.bugflix.weblog.like.domain;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class LikeKey implements Serializable {
    private Long userId;
    private Long postId;

    public LikeKey(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeKey likeKey = (LikeKey) o;
        return Objects.equals(userId, likeKey.userId) && Objects.equals(postId, likeKey.postId);

    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
