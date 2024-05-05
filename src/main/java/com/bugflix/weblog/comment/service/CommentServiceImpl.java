package com.bugflix.weblog.comment.service;

import com.bugflix.weblog.comment.domain.Comment;
import com.bugflix.weblog.comment.dto.CommentRequest;
import com.bugflix.weblog.comment.dto.CommentResponse;
import com.bugflix.weblog.comment.repository.CommentRepository;
import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.NoOwnershipException;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.post.repository.PostRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void saveComment(CommentRequest commentRequest, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException(Errors.POST_NOT_FOUND));

        Comment comment;
        if (commentRequest.getParentCommentId() != null) {
            Comment parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(() -> new ResourceNotFoundException(Errors.PARENT_COMMENT_NOT_FOUND));
            valdiateIsOnSamePost(post, parentComment);
            comment = Comment.of(commentRequest.getContent(), user, post, parentComment);
        } else {
            comment = Comment.of(commentRequest.getContent(), user, post);
        }

        commentRepository.save(comment);
    }

    private void valdiateIsOnSamePost(Post post, Comment parentComment) {
        if (!Objects.equals(post.getPostId(), parentComment.getPost().getPostId())) {
            throw new RuntimeException("대댓글은 댓글과 같은 포스트 상에 존재해야 합니다.");
        }
    }

    public void updateComment(Long commentId, String content, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.COMMENT_NOT_FOUND));
        validateIsOwnedComment(userDetails, comment);
        /*
         todo: 1.시큐리티 컨텍스트의 UserDetails와 comment의 소유주인 User가
          서로 다른 엔티티 매니저에 의해 관리되면 조건문이 계속 false가 되진 않는지
         todo: 2.Transactional 어노테이션을 사용하지 않아도 comment.getUser()가 가능한가?
          => ManyToOne 연관관계로 기본 Fetch 전략이 EAGER
         */
        comment.updateContent(content);
        commentRepository.save(comment);
    }

    private void validateIsOwnedComment(UserDetails userDetails, Comment comment) {
        Long userId = ((CustomUserDetails) userDetails).getUser().getUserId();
        if (!Objects.equals(comment.getUser().getUserId(), userId)) {
            throw new NoOwnershipException(Errors.IS_NOT_MINE);
        }
    }

    public void deleteComment(Long commentId, UserDetails userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.COMMENT_NOT_FOUND));
        validateIsOwnedComment(userDetails, comment);
        commentRepository.delete(comment);
    }


    public List<CommentResponse> getCommentsByPostId(Long postId) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        List<Comment> comments = commentRepository.findAllByPostPostId(postId);

        for (Comment comment : comments) {
            User user = comment.getUser();
            commentResponses.add(CommentResponse.of(comment, user, user.getProfile()));
        }

        return commentResponses;
    }

    public List<CommentResponse> getCommentsByPostIdWithPaging(Long postId, Integer offset, Integer limit) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        Page<Comment> comments = commentRepository.findAllByPostPostId(postId,
                PageRequest.of(offset, limit));

        for (Comment comment : comments) {
            User user = comment.getUser();
            commentResponses.add(CommentResponse.of(comment, user, user.getProfile()));
        }
        return commentResponses;
    }
}
