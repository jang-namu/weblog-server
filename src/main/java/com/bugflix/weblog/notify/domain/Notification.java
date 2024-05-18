package com.bugflix.weblog.notify.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "notification_tb")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private Boolean isRead;

    public void read() {
        isRead = true;
    }

    public enum NotificationType {
        SYSTEM, COMMENT, REPLY, FOLLOW;
    }

    public Notification(String content, String url, User receiver, User sender, NotificationType type) {
        this.content = content;
        this.url = url;
        this.receiver = receiver;
        this.sender = sender;
        this.type = type;
        this.isRead = false;
    }

    public static Notification of(String content, String url, User receiver, User sender, NotificationType type) {
        return new Notification(content, url, receiver, sender, type);
    }
}
