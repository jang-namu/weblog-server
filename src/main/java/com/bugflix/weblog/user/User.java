package com.bugflix.weblog.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.post.Post;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.LAZY)
    private List<Post> posts;

}
