package post;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import page.Page;
import post.dto.PostRequest;
import post.dto.PostResponse;
import user.User;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private Long like;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date update_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="page_id",nullable = false)
    private Page page;


    public Post(PostRequest postRequest){
        title = postRequest.getTitle();;
        content = postRequest.getContent();
        like = postRequest.getLike();
    }
}
