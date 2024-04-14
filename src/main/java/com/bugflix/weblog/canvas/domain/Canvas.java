package com.bugflix.weblog.canvas.domain;

import com.bugflix.weblog.canvas.dto.CanvasRequest;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "canvas_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Canvas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "canvas_id")
    private Long canvasId;
    private String title;
    @Column(name = "\"key\"") // GenerationTarget encountered exception accepting command : Error executing DDL -> 예약어가 겹치는 듯
    private String key;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Canvas(String title, String key, User user) {
        this.title = title;
        this.key = key;
        this.user = user;
    }

    public static Canvas of(CanvasRequest request, User user) {
        return new Canvas(request.getTitle(), request.getKey(), user);
    }
}
