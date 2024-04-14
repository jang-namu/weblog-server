package com.bugflix.weblog.canvas.service;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.canvas.dto.CanvasRequest;
import com.bugflix.weblog.canvas.repository.CanvasRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CanvasService {

    private final CanvasRepository canvasRepository;

    public void save(CanvasRequest request, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Canvas canvas = Canvas.of(request, user);
        canvasRepository.save(canvas);
    }
}
