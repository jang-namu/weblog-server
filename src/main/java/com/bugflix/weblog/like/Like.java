package com.bugflix.weblog.like;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity(name = "like_tb")
@NoArgsConstructor
@Getter
public class Like {

    @EmbeddedId
    private LikeKey id;

    public Like(Long userId, Long postId) {
        this.id = new LikeKey(userId,postId);

    }

/*    public void updateId(LikeKey id) {
        this.id = id;
    }*/
}
