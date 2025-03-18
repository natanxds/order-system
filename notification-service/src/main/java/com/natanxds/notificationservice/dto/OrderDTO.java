package com.natanxds.notificationservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public record OrderDTO(
    Long id,
    String customerName,
    LocalDateTime orderDate,
    OrderStatus status,
    Set<OrderItemResponse> items,
    BigDecimal totalAmount
) {}
