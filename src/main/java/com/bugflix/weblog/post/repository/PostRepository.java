package com.bugflix.weblog.post.repository;

import com.bugflix.weblog.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPageUrl(String url);

    List<Post> findByPageUrlAndUserUserId(String url, Long userId);

    @Query("select p from post_tb p where p.modifiedDate > :criterion order by p.likeCount desc")
    List<Post> findWithPagination(LocalDateTime criterion, Pageable pageable);

    @Query("select p from post_tb p " +
            "right join tag_tb t on t.post = p " +
            "join user_tb u on p.user = u " +
            "where t.tagContent = :query")
    List<Post> findPostsByTagContent(String query, Pageable pageable);

    @Query("select p from post_tb p join fetch p.tags join fetch p.user where p.title like %:query%")
    List<Post> findPostsByTitleLike(String query, Pageable pageable);
}
