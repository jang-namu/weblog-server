package com.bugflix.weblog.like.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.like.domain.Like;
import com.bugflix.weblog.like.dto.LikeStatusResponse;
import com.bugflix.weblog.like.repository.LikeRepository;
import com.bugflix.weblog.notify.service.NotificationService;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.post.repository.PostRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    public boolean isLiked(Long postId, Long userId) {
        return likeRepository.existsLikeById_PostIdAndId_UserId(postId, userId);
    }

    public Long  countLikes(Long postId) {
        return likeRepository.countById_PostId(postId);
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
    @Transactional
    public LikeStatusResponse changeLikeStatus(Long postId, UserDetails userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Errors.POST_NOT_FOUND));
        User user = ((CustomUserDetails)userDetails).getUser();

        Like like = likeRepository.findById_UserIdAndId_PostId(user.getUserId(), postId).orElse(null);

        if (like != null) {
            likeRepository.delete(like);      // 본인의 Like 상태 변경 Logic
            post.setUnLike();
            return new LikeStatusResponse(likeRepository.countById_PostId(postId), false);
        }
        likeRepository.save(new Like(user.getUserId(), postId));
        post.setLike();
        notificationService.notifyLike(post.getUser(), user);
        return new LikeStatusResponse(likeRepository.countById_PostId(postId), true);

    }
}
