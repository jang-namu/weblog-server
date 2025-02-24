package com.bugflix.weblog.follow.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "follow_tb")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"follower_id","following_id"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    @Builder
    public Follow(User follower, User following){
        if (Objects.equals(follower.getUserId(), following.getUserId())) {
            throw new IllegalArgumentException("Can't add self follow");
        }
        this.follower = follower;
        this.following = following;
    }

}
