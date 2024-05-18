package com.bugflix.weblog.notify.controller;

import com.bugflix.weblog.notify.servoce.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
        log.info("Last-Event-Id: {}", lastEventId);
        // todo: lastEventId -> 유실된 Message 전송
        return notificationService.subscribe(userDetails);
    }

//    @Operation(summary = "sse 테스트", description = "sse 전송")
//    @PostMapping(value = "/v1/sse/test/{userId}")
//    public void sendMessage(@PathVariable(required = false) Long userId) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
//        notificationService.notifySystem(user, "테스트 메시지 전송");
//    }
}
