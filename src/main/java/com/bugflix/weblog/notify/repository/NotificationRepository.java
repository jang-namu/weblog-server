package com.bugflix.weblog.notify.repository;

import com.bugflix.weblog.notify.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
