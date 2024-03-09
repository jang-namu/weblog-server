package com.bugflix.weblog.comment.service;

import com.bugflix.weblog.comment.domain.Comment;
import com.bugflix.weblog.comment.dto.CommentRequest;
import com.bugflix.weblog.comment.repository.CommentRepository;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.post.repository.PostRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * Name : saveComment
     * Parameter :
     * - CommentRequest commentRequest : 댓글 작성 요청
     * Return :
     * - void
     * Explanation :
     * - 인증된 사용자의 Post에 대한 댓글 작성 요청을 받음.
     * - Post 정보를 DB에 저장
     * - 성공 : void
     * - 실패 : Exception 반환
     */
    public void saveComment(CommentRequest commentRequest, UserDetails userDetails) throws Exception {
        // 1. User 객체 검색
        User user = ((CustomUserDetails) userDetails).getUser();

        // 2. Post 객체 검색
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("invalid postId"));

        // 3. Comment 객체 생성
        Comment comment;
        if (commentRequest.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("parentComment를 찾을 수 없습니다."));
            comment = Comment.of(commentRequest.getContent(), user, post, parentComment);
        } else {
            comment = Comment.of(commentRequest.getContent(), user, post);
        }

        // 4. Comment 저장
        commentRepository.save(comment);
    }

    public void updateComment(Long commentId, String content, UserDetails userDetails) throws Exception {
        // 1. User 객체 검색
        User user = ((CustomUserDetails) userDetails).getUser();

        // 2. commentId로 Comment 검색
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("invalid commentId"));

        /*
         todo: 1.시큐리티 컨텍스트의 UserDetails와 comment의 소유주인 User가
          서로 다른 엔티티 매니저에 의해 관리되면 조건문이 계속 false가 되진 않는지
         todo: 2.Transactional 어노테이션을 사용하지 않아도 comment.getUser()가 가능한가?
          => ManyToOne 연관관계로 기본 Fetch 전략이 EAGER
         */
        if (comment.getUser() == user) {
            // 3. Comment 의 내용 update
            comment.updateContent(content);
        } else {
            throw new IllegalArgumentException("Only writer can update");
        }
    }

    public void deleteComment(Long commentId, UserDetails userDetails) throws Exception {
        // 1. userDetail 에서 user 정보 받아오기
        User user = ((CustomUserDetails) userDetails).getUser();

        // 2. commentId로 Comment 검색
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("invalid commentId"));

        // 3. user 정보와 comment 의 소유자가 동일한지 확인
        if (comment.getUser() == user) {
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("Only writer can delete");
        }
    }
}
