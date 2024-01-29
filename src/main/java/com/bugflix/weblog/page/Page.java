package com.bugflix.weblog.page;

import com.bugflix.weblog.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.post.Post;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "page_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id")
    private Long id;

    @Getter
    private String url;

    @OneToMany(mappedBy = "page")
    private List<Post> posts = new ArrayList<>();

    public Page(PostRequest postRequest) {
        this.url = postRequest.getUrl();
    }

    public Page(String url) {
        this.url = url;
    }

}
