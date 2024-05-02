package com.bugflix.weblog.notification.service;

import com.bugflix.weblog.notification.repository.SseEmitterRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class SseService {

    private final SseEmitterRepository sseEmitterRepository;

    public SseEmitter createSseEmitter(UserDetails userDetails) throws IOException {
        User user = ((CustomUserDetails) userDetails).getUser();

        SseEmitter emitter = new SseEmitter();
        sseEmitterRepository.add(user.getNickname(), emitter);
        emitter.send(SseEmitter.event()
                .name("first")
                .data("Success to make connection"));
        return emitter;
    }

    public void sendNotification(String doingSomething) {
        Collection<SseEmitter> emitters = sseEmitterRepository.getEmitters();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("event").data(doingSomething));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
