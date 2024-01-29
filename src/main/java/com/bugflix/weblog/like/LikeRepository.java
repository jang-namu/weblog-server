package com.bugflix.weblog.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Like findLikeById_PostIdAndId_UserId(Long postId, Long userId);

    List<Like> findById_PostId(Long postId);

    boolean existsLikeById_PostIdAndId_UserId(Long postId, Long userId);

    Long countByIdPostId(Long postId);
}

