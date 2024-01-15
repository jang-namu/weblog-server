package com.bugflix.weblog.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.post.Post;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user_tb")
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User(String loginId,String password,String nickname){
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
    }
}
