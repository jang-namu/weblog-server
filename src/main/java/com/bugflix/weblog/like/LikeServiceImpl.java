package com.bugflix.weblog.like;

import com.bugflix.weblog.like.dto.LikeStatus;
import com.bugflix.weblog.user.User;
import lombok.RequiredArgsConstructor;
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
     * - LikeStatus
     * - likeCount : 좋아요 갯수
     * - isLiked : 본인의 좋아요 상태
     * <p>
     * Explanation :
     * - 이미 Post 에 Like 표시를 한 경우 : DB 에서 Like Entity 삭제 && isLiked == false && likeCount -= 1
     * - 그 반대의 경우 : DB 에 Like Entity 추가 && isLiked == true && likeCount += 1
     * <p>
     * - isLiked 와 likeCount 를 LikeStatus 로 encapsulation 하여 반환
     */
    public LikeStatus changeLikeStatus(Long postId) {
        LikeStatus likeStatus = new LikeStatus();

        Long userId = 1L;  // Todo User 정보 받아오기

        Like like = likeRepository.findLikeById_PostIdAndId_UserId(postId, userId);

        if (like != null) likeRepository.delete(like);      // 본인의 Like 상태 변경 Logic
        else {
            likeRepository.save(new Like(userId, postId));
            likeStatus.setLiked(true);
        }

        likeStatus.setLikeCount(likeRepository.countByIdPostId(postId));    // Like Count 변경 Logic

        return likeStatus;
    }
}
