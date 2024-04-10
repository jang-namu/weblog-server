package com.bugflix.weblog.like.repository;

import com.bugflix.weblog.like.domain.Like;
import com.bugflix.weblog.like.domain.LikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, LikeKey> {
    Optional<Like> findById_UserIdAndId_PostId(Long userId, Long postId);

    boolean existsLikeById_PostIdAndId_UserId(Long userId, Long postId);

    Long countById_PostId(Long postId);
}

