package com.bugflix.weblog.comment.repository;

import com.bugflix.weblog.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
