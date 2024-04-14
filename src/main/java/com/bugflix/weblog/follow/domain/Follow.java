package com.bugflix.weblog.follow.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "follow_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User following;

    @Builder
    public Follow(User follower, User following){
        this.follower = follower;
        this.following = following;
    }

}
