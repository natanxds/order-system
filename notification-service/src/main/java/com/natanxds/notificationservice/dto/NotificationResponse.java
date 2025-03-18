package com.natanxds.notificationservice.dto;

import com.natanxds.notificationservice.domain.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(
    Long id,
    Long orderId,
    String customerName,
    NotificationType type,
    String message,
    LocalDateTime createdAt,
    boolean sent,
    LocalDateTime sentAt
) {} 