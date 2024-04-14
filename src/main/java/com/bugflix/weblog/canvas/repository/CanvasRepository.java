package com.bugflix.weblog.canvas.repository;

import com.bugflix.weblog.canvas.domain.Canvas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanvasRepository extends JpaRepository<Canvas, Long> {
}
