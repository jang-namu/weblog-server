package com.bugflix.weblog.notification.controller;

import com.bugflix.weblog.notification.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping(("/api"))
@RequiredArgsConstructor
public class NotificationController {
    private final SseService sseService;

    @GetMapping(value = "/v1/notifications/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        return ResponseEntity.ok(sseService.createSseEmitter(userDetails));
    }

    @GetMapping(value="/v1/notifications/test")
    public void test() {
        sseService.sendNotification("doing something");
    }
}
