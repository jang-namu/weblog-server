package com.bugflix.weblog.page;

import com.bugflix.weblog.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.post.Post;

import java.util.List;

@Entity(name = "page_tb")
@NoArgsConstructor
public class Page {

    @Id @Column(name = "page_id") @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String url;

    @OneToMany(mappedBy = "page",cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.LAZY)
    private List<Post> posts;

    public Page(PostRequest postRequest){
        this.url = postRequest.getUrl();
    }
    public Page(String url){this.url = url;}

}
