package post;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import post.dto.PostRequest;
import post.dto.PostResponse;
import user.User;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long like;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_date;

    @Temporal(TemporalType.TIMESTAMP)
    private Date update_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Post(PostRequest postRequest){
        title = postRequest.getTitle();;
        content = postRequest.getContent();
        like = postRequest.getLike();
    }
}
