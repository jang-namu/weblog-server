package com.bugflix.weblog.notify.controller;

import com.bugflix.weblog.notify.dto.NotificationResponse;
import com.bugflix.weblog.notify.servoce.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "SSE Connect", description = "SSE 커넥션을 맺습니다.")
    @GetMapping(value = "/v1/sse/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam(required = false, defaultValue = "") String lastEventId) {
//        log.info("Last-Event-Id: {}", lastEventId);
        // todo: lastEventId -> 유실된 Message 전송
        return notificationService.subscribe(userDetails);
    }

//    @Operation(summary = "sse 테스트", description = "sse 전송")
//    @PostMapping(value = "/v1/sse/test/{userId}")
//    public void sendMessage(@PathVariable(required = false) Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
//        notificationService.notifySystem(user, "테스트 메시지 전송");
//    }

    @Operation(summary = "알림조회", description = "알림을 조회합니다.")
    @GetMapping(value = "/v1/notifications")
    public ResponseEntity<List<NotificationResponse>> getNotifications(@RequestParam(required = false, defaultValue = "0") Integer offset,
                                                                       @RequestParam(required = false, defaultValue = "6") Integer limit,
                                                                       @RequestParam(required = false, defaultValue = "false") Boolean read,
                                                                       @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(notificationService.getNotifications(userDetails, read, offset, limit));
    }


    @Operation(summary = "알림 읽음처리", description = "알림을 읽음 처리합니다.")
    @PatchMapping(value = "/v1/notifications/{notificationId}")
    public ResponseEntity<?> readNotifications(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long notificationId) {
        notificationService.readNotifcations(userDetails, notificationId);
        return ResponseEntity.ok().build();
    }
}
