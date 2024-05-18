package com.bugflix.weblog.notify.repository;

import com.bugflix.weblog.notify.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from notification_tb n " +
           "join fetch n.receiver u " +
           "where u.userId= :userId and n.isRead = :isRead")
    Page<Notification> findByReceiverUserIdWhereNotRead(Long userId, Boolean isRead, Pageable pageable);
}
