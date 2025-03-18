package com.natanxds.notificationservice.repository;

import com.natanxds.notificationservice.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByOrderId(Long orderId);
    List<Notification> findBySentFalse();
} 