package com.bugflix.weblog.canvas.repository;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanvasRepository extends JpaRepository<Canvas, Long> {
    Page<Canvas> findAllByUser(User user, Pageable pageable);

}
