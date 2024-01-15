package com.bugflix.weblog.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public boolean isLiked(Long postId,Long userId){
        return likeRepository.existsLikeById_PostIdAndId_UserId(postId,userId);
    }

    public Long countLikes(Long postId){
        return likeRepository.countByIdPostId(postId);
    }
}
