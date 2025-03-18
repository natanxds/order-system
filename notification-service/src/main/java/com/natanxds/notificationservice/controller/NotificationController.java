package com.natanxds.notificationservice.controller;

import com.natanxds.notificationservice.dto.NotificationResponse;
import com.natanxds.notificationservice.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


public class NotificationController {
//
//    private final NotificationService notificationService;
//
//    @GetMapping("/{id}")
//    @Operation(
//        summary = "Get notification by ID",
//        description = "Retrieves a notification by its ID"
//    )
//    @ApiResponse(
//        responseCode = "200",
//        description = "Notification found",
//        content = @Content(schema = @Schema(implementation = NotificationResponse.class))
//    )
//    @ApiResponse(
//        responseCode = "404",
//        description = "Notification not found"
//    )
//    public ResponseEntity<NotificationResponse> getNotification(
//            @Parameter(description = "Notification ID", required = true)
//            @PathVariable Long id) {
//        return ResponseEntity.ok(notificationService.getNotification(id));
//    }
//
//    @GetMapping("/order/{orderId}")
//    @Operation(
//        summary = "Get notifications by order ID",
//        description = "Retrieves all notifications for a specific order"
//    )
//    public ResponseEntity<List<NotificationResponse>> getNotificationsByOrderId(
//            @Parameter(description = "Order ID", required = true)
//            @PathVariable Long orderId) {
//        return ResponseEntity.ok(notificationService.getNotificationsByOrderId(orderId));
//    }
//
//    @GetMapping("/pending")
//    @Operation(
//        summary = "Get pending notifications",
//        description = "Retrieves all notifications that haven't been sent yet"
//    )
//    public ResponseEntity<List<NotificationResponse>> getPendingNotifications() {
//        return ResponseEntity.ok(notificationService.getPendingNotifications());
//    }
//
//    @PatchMapping("/{id}/mark-sent")
//    @Operation(
//        summary = "Mark notification as sent",
//        description = "Marks a notification as sent and records the sent timestamp"
//    )
//    @ApiResponse(
//        responseCode = "200",
//        description = "Notification marked as sent",
//        content = @Content(schema = @Schema(implementation = NotificationResponse.class))
//    )
//    @ApiResponse(
//        responseCode = "404",
//        description = "Notification not found"
//    )
//    public ResponseEntity<NotificationResponse> markAsSent(
//            @Parameter(description = "Notification ID", required = true)
//            @PathVariable Long id) {
//        return ResponseEntity.ok(notificationService.markAsSent(id));
//    }
}