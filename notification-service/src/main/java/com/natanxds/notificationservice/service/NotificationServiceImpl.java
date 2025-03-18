package com.natanxds.notificationservice.service;

import com.natanxds.notificationservice.domain.Notification;
import com.natanxds.notificationservice.domain.NotificationType;
import com.natanxds.notificationservice.dto.NotificationResponse;
import com.natanxds.notificationservice.dto.OrderDTO;
import com.natanxds.notificationservice.repository.NotificationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @KafkaListener(topicPartitions = @TopicPartition(topic = "order-created", partitions = {"0"}), containerFactory = "orderKafkaListenerContainerFactory")
    public void orderCreatedListener(OrderDTO orderDTO) {
        String message = String.format("Order %d created successfully", orderDTO.id());
        System.out.println(message);
    }

    @Override
    public NotificationResponse createNotification(Long orderId, String customerName, NotificationType type, String message) {
        Notification notification = Notification.builder()
                .orderId(orderId)
                .customerName(customerName)
                .type(type)
                .message(message)
                .build();

        return mapToResponse(notificationRepository.save(notification));
    }

    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotification(Long id) {
        return notificationRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByOrderId(Long orderId) {
        return notificationRepository.findByOrderId(orderId).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificationResponse> getPendingNotifications() {
        return notificationRepository.findBySentFalse().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public NotificationResponse markAsSent(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found with id: " + id));

        notification.setSent(true);
        notification.setSentAt(LocalDateTime.now());

        return mapToResponse(notificationRepository.save(notification));
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getOrderId(),
                notification.getCustomerName(),
                notification.getType(),
                notification.getMessage(),
                notification.getCreatedAt(),
                notification.isSent(),
                notification.getSentAt()
        );
    }
} 