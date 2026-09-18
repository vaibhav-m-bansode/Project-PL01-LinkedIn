package com.vaibhavbansode.notificationService.repository;

import com.vaibhavbansode.notificationService.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}