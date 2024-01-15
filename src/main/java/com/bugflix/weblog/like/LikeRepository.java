package com.bugflix.weblog.like;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,LikeKey> {
    List<Like> findById_PostIdAndId_UserId(Long postId,Long userId);
    List<Like> findById_PostId(Long postId);

    boolean existsLikeById_PostIdAndId_UserId(Long postId,Long userId);

    Long countByIdPostId(Long postId);
}

