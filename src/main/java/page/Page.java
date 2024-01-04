package page;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.Post;

import java.util.List;

@Entity
@NoArgsConstructor
public class Page {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String url;

    @OneToMany(mappedBy = "page",cascade = CascadeType.ALL, orphanRemoval = true,fetch=FetchType.LAZY)
    private List<Post> posts;

}
