package com.bugflix.weblog.canvas.controller;

import com.bugflix.weblog.canvas.dto.CanvasRequest;
import com.bugflix.weblog.canvas.dto.CanvasResponse;
import com.bugflix.weblog.canvas.dto.CanvasSearchRequest;
import com.bugflix.weblog.canvas.service.CanvasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class CanvasController {

    private final CanvasService canvasService;

    @PostMapping("/v1/canvases")
    public ResponseEntity<?> saveCanvas(@RequestBody CanvasRequest request,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.save(request, userDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/v1/canvases/{canvasId}")
    public ResponseEntity<?> updateCanvas(@PathVariable long canvasId,
                                          @RequestBody CanvasRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.update(canvasId, request, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/v1/canvases/{canvasId}")
    public ResponseEntity<?> deleteCanvas(@PathVariable long canvasId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        canvasService.delete(canvasId, userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/v1/canvases")
    public ResponseEntity<List<CanvasResponse>> getRecentCanvases(@RequestParam Integer offset, @RequestParam Integer limit) {
        return ResponseEntity.ok().body(canvasService.getRecentCanvases(offset, limit));
    }

    @GetMapping("/v1/canvases/mine")
    public ResponseEntity<List<CanvasResponse>> getMyCanvases(@RequestParam Integer offset, @RequestParam Integer limit,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(canvasService.getMyCanvases(offset, limit, userDetails));
    }

    @GetMapping("/v1/search/canvases")
    public ResponseEntity<List<CanvasResponse>> searchCanvases(@ModelAttribute CanvasSearchRequest request) {
        return ResponseEntity.ok().body(canvasService.search(request));
    }
}
