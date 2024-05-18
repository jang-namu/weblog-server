package com.bugflix.weblog.notify.servoce;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.NoOwnershipException;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.notify.domain.Notification;
import com.bugflix.weblog.notify.dto.NotificationResponse;
import com.bugflix.weblog.notify.repository.NotificationRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private static final Long DEFAULT_TIMEOUT = 120L * 1000;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final NotificationRepository notificationRepository;


    public SseEmitter subscribe(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        Long memberId = ((CustomUserDetails) userDetails).getUser().getUserId();
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.put(memberId, emitter);

        emitter.onTimeout(() -> {
            emitter.complete();
            emitters.remove(memberId);
            log.info("Emitter timeout, will be removed and client should reconnect");

        });
        emitter.onError((e) -> {
            emitter.completeWithError(e);
            emitters.remove(memberId);
            log.error("Emitter error, removed and client should reconnect", e);

        });
        emitter.onCompletion(() -> this.emitters.remove(memberId));

//        sendNotification("connection created", "", user, user, Notification.NotificationType.SYSTEM);
        Notification notification = Notification.of("connection created", "", user, user, Notification.NotificationType.SYSTEM);
        redisTemplate.convertAndSend("notificationTopic", NotificationResponse.from(notification));


        return emitter;
    }

    public void notifyComment(User receiver, User sender) {
        String message = sender.getNickname() + "님이 " + receiver.getNickname()  + "새로운 댓글을 작성했습니다.";
        String url = "";
        sendNotification(message, url, receiver, sender, Notification.NotificationType.COMMENT);
    }

    public void notifyReply(User receiver, User sender) {
        String message = sender.getNickname() + "님이 새로운 답글을 작성했습니다.";
        String url = "";
        sendNotification(message, url, receiver, sender, Notification.NotificationType.REPLY);
    }

    public void notifyFollow(User receiver, User sender) {
        String message = sender.getNickname() + "님이 회원님을 팔로우하기 시작했습니다";
        String url = "";
        sendNotification(message, url, receiver, sender, Notification.NotificationType.FOLLOW);
    }

    public void notifyLike(User receiver, User sender) {
        String message = sender.getNickname() + "님이 회원님의 게시글에 좋아요를 추가했습니다.";
        String url = "";
        sendNotification(message, url, receiver, sender, Notification.NotificationType.FOLLOW);
    }

    public void sendNotification(String message, String url, User receiver, User sender, Notification.NotificationType type) {
        Notification notification = Notification.of(message, url, receiver, sender, type);
        publishNotification(notification);
    }

    protected void publishNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        redisTemplate.convertAndSend("notificationTopic", NotificationResponse.from(notification));
    }

    @Bean
    RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener((message, channel) -> {
            String body = redisTemplate.getStringSerializer().deserialize(message.getBody());
            NotificationResponse notificationResponse = null;

            try {
                objectMapper.findAndRegisterModules();
                notificationResponse = objectMapper.readValue(body, NotificationResponse.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            assert notificationResponse != null;

            consumeNotification(notificationResponse);
        }, new PatternTopic("notificationTopic"));
        return container;
    }

    /*
        SseEmitters에 대한 정보를 메모리상 ConcurrentMap으로 보관.
        -> 다중 WAS 환경에서는 각 서버가 지닌 SseEmitters 정보가 다름
        즉, 현재 찾고있는 Emitter가 현재 서버에 없을수도 있다.
        => Redis pub/sub은 모든 subscriber에게 동일한 메시지를 보낸다.(하나의 컨슈머에게 보내는 카프카와는 다름)
        모든 WAS는 동일한 메시지를 받고 해당되는 Emitter를 가진 서버가 이를 처리한다.
        따라서 emitter가 null이 아닌지 체크하는 로직이 필요하다.
     */
    private void consumeNotification(NotificationResponse notificationResponse) {
        SseEmitter emitter = emitters.get(notificationResponse.getReceiver().getUserId());
        if (emitter != null) {
            // todo: 스레드 적용 전, 후 성능 측정
            executor.execute(() -> {
                try {
                    emitter.send(SseEmitter.event()
                            .id(notificationResponse.getReceiver().getUserId().toString())
                            .name("notification")
                            .data(notificationResponse)
                            .reconnectTime(1000L));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                    emitters.remove(notificationResponse.getReceiver().getUserId());
                    throw new RuntimeException("Failed to send SSE message");
                }
            });

        }
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotifications(UserDetails userDetails, Boolean isRead, Integer offset, Integer limit) {
        Long userId = ((CustomUserDetails) userDetails).getUser().getUserId();
        Sort strategy = Sort.by(Sort.Direction.DESC, "createdDate");

        Page<Notification> notifications = notificationRepository.findByReceiverUserIdWhereNotRead(userId, isRead,
                PageRequest.of(offset, limit, strategy));
        log.info("notifications size: " + notifications.getTotalElements());
        return notifications.stream().map(NotificationResponse::from).toList();
    }

    public void readNotifcations(UserDetails userDetails, Long notificationId) {
        Long userId = ((CustomUserDetails) userDetails).getUser().getUserId();
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.NOTIFICATION_NOT_FOUND));
        validateIsOwnedNotification(notification, userId);

        notification.read();
        notificationRepository.save(notification);
    }

    public void validateIsOwnedNotification(Notification notification, Long userId) {
        if (!Objects.equals(notification.getReceiver().getUserId(), userId)) {
            throw new NoOwnershipException(Errors.IS_NOT_MINE);
        }
    }
}

