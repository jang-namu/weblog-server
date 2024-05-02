package com.bugflix.weblog.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SseEmitterRepository {
    private static final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(String nickname, SseEmitter sseEmitter) {
        emitters.put(nickname, sseEmitter);
        return sseEmitter;
    }

    public Collection<SseEmitter> getEmitters() {
        return emitters.values();
    }
}
