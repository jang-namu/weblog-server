package com.bugflix.weblog.like.service;

import com.bugflix.weblog.like.domain.Like;
import com.bugflix.weblog.like.dto.LikeStatusResponse;
import com.bugflix.weblog.like.repository.LikeRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl {
    private final LikeRepository likeRepository;

    public boolean isLiked(Long postId, Long userId) {
        return likeRepository.existsLikeById_PostIdAndId_UserId(postId, userId);
    }

    public Long countLikes(Long postId) {
        return likeRepository.countByIdPostId(postId);
    }

    /**
     * Name : changeLikeStatus
     * Parameter :
     * - Long postId
     * return :
     * - LikeStatusResponse
     * - likeCount : 좋아요 갯수
     * - isLiked : 본인의 좋아요 상태
     * <p>
     * Explanation :
     * - 이미 Post 에 Like 표시를 한 경우 : DB 에서 Like Entity 삭제 && isLiked == false && likeCount -= 1
     * - 그 반대의 경우 : DB 에 Like Entity 추가 && isLiked == true && likeCount += 1
     * <p>
     * - isLiked 와 likeCount 를 LikeStatusResponse 로 encapsulation 하여 반환
     */
    public LikeStatusResponse changeLikeStatus(Long postId, UserDetails userDetails) throws Exception {

        Long userId = ((CustomUserDetails)userDetails).getUser().getUserId();

        Like like = likeRepository.findLikeById_PostIdAndId_UserId(postId, userId).orElseThrow(Exception::new);

        if (like != null) {
            likeRepository.delete(like);      // 본인의 Like 상태 변경 Logic
            return new LikeStatusResponse(likeRepository.countByIdPostId(postId), false);
        }
        likeRepository.save(new Like(userId, postId));
        return new LikeStatusResponse(likeRepository.countByIdPostId(postId), true);

    }
}
