package com.bugflix.weblog.like.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "like_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Like {

    @EmbeddedId
    private LikeKey id;

    public Like(Long userId, Long postId) {
        this.id = new LikeKey(userId, postId);

    }
}
