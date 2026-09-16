package com.vaibhavbansode.notificationService.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.vaibhavbansode.notificationService.entity.Notification}
 */
public record NotificationDto(Long id, String message, Long userId, LocalDateTime createdAt) implements Serializable {
}