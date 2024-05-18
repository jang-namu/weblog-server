package com.bugflix.weblog.notify.dto;

import com.bugflix.weblog.notify.domain.Notification;
import com.bugflix.weblog.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationResponse {

    private Long notificationId;
    private String content;
    private String url;
    private UserInfo receiver;
    private UserInfo sender;
    private Notification.NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;

    @Getter(AccessLevel.PUBLIC)
    public static class UserInfo {
        private final Long userId;
        private final String nickname;

        @Builder(access = AccessLevel.PRIVATE)
        private UserInfo(Long userId, String nickname) {
            this.userId = userId;
            this.nickname = nickname;
        }

        private static UserInfo from(User user) {
            return UserInfo.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .build();
        }
    }


    @Builder(access = AccessLevel.PRIVATE)
    public NotificationResponse(Long notificationId, String content, String url, UserInfo receiver, UserInfo sender,
                                Notification.NotificationType type, Boolean isRead, LocalDateTime createdAt) {
        this.notificationId = notificationId;
        this.content = content;
        this.url = url;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public static NotificationResponse from(Notification notification) {
        return NotificationResponse.builder()
                .notificationId(notification.getId())
                .content(notification.getContent())
                .url(notification.getUrl())
                .receiver(UserInfo.from(notification.getReceiver()))
                .sender(UserInfo.from(notification.getSender()))
                .type(notification.getType())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedDate())
                .build();
    }
}
