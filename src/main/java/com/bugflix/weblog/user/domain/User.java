package com.bugflix.weblog.user.domain;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.comment.domain.Comment;
import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.profile.domain.Profile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Entity(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Profile profile;

    // todo: open-in-view: false이고 LAZY로 Fetch 시, 로그인 토큰 생성 시점에 세션이 끊겨서 LazyInitial~Exception 발생
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Authority> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Canvas> canvases = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickname, Profile profile, List<Authority> roles) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void assignRoles(List<Authority> roles) {
        this.roles = roles.stream().map(authority -> {
            authority.assignUser(this);
            return authority;
        }).collect(Collectors.toList());
    }

    public User update(String nickname, String picture) {
        this.nickname = nickname;
        this.profile.changeProfileImage(picture);
        return this;
    }

    public void assignProfile(Profile profile) {
        this.profile = profile;
    }

}
