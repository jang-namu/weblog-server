package com.bugflix.weblog.canvas.service;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.canvas.dto.CanvasRequest;
import com.bugflix.weblog.canvas.dto.CanvasResponse;
import com.bugflix.weblog.canvas.repository.CanvasRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CanvasService {

    private final CanvasRepository canvasRepository;

    public void save(CanvasRequest request, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Canvas canvas = Canvas.of(request, user);
        canvasRepository.save(canvas);
    }

    public void update(long canvasId, CanvasRequest request, UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUser().getUserId();
        Canvas canvas = canvasRepository.findById(canvasId).orElseThrow(() -> new IllegalArgumentException("캔버스를 찾을 수 없습니다."));
        validateIsOwnedCanvas(canvas, userId);
        canvas.update(request);
        canvasRepository.save(canvas);
    }

    private void validateIsOwnedCanvas(Canvas canvas, Long userId) {
        if (!Objects.equals(userId, canvas.getUser().getUserId())) {
            throw new RuntimeException("소유하지 않은 Canvas입니다.");
        }
    }

    public void delete(long canvasId, UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getUser().getUserId();
        Canvas canvas = canvasRepository.findById(canvasId).orElseThrow(() -> new IllegalArgumentException("캔버스를 찾을 수 없습니다."));
        validateIsOwnedCanvas(canvas, userId);
        canvasRepository.delete(canvas);
    }

    @Transactional(readOnly = true)
    public List<CanvasResponse> getRecentCanvases(Integer offset, Integer limit) {
        Sort strategy = Sort.by(Sort.Direction.DESC, "canvasId");
        Page<Canvas> canvases = canvasRepository.findAll(PageRequest.of(offset, limit, strategy));
        return canvases.stream()
                .map(canvas -> CanvasResponse.of(canvas, canvas.getUser())).toList();
    }
}
