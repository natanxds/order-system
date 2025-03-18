package com.natanxds.notificationservice.service;

import com.natanxds.notificationservice.domain.NotificationType;
import com.natanxds.notificationservice.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse createNotification(Long orderId, String customerName, NotificationType type, String message);
    NotificationResponse getNotification(Long id);
    List<NotificationResponse> getNotificationsByOrderId(Long orderId);
    List<NotificationResponse> getPendingNotifications();
    NotificationResponse markAsSent(Long id);
} 