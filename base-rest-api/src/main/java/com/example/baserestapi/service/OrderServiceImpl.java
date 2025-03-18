package com.example.baserestapi.service;

import com.example.baserestapi.domain.Order;
import com.example.baserestapi.domain.OrderItem;
import com.example.baserestapi.domain.OrderStatus;
import com.example.baserestapi.dto.OrderDTO;
import com.example.baserestapi.dto.OrderItemResponse;
import com.example.baserestapi.dto.OrderRequest;
import com.example.baserestapi.dto.OrderResponse;
import com.example.baserestapi.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderResponse> kafkaTemplateOrder;
    private final Random random = new Random();

    public OrderServiceImpl(OrderRepository orderRepository, KafkaTemplate<String, OrderResponse> kafkaTemplateOrder) {
        this.orderRepository = orderRepository;
        this.kafkaTemplateOrder = kafkaTemplateOrder;
    }

    @Override
    @SuppressWarnings("null")
    public OrderResponse createOrder(OrderRequest request) {
        Order order = Order.builder()
                .customerName(request.customerName())
                .items(request.items().stream()
                        .map(item -> OrderItem.builder()
                                .productName(item.productName())
                                .quantity(item.quantity())
                                .price(item.price())
                                .build())
                        .collect(Collectors.toSet()))
                .build();

        order.recalculateTotal();
        Order savedOrder = orderRepository.save(order);
        OrderResponse orderResponse = mapToResponse(savedOrder);
        
        Integer partition = random.nextInt(2);
        kafkaTemplateOrder.send("order-created", partition, null, orderResponse);
        log.info(
                "Order created and sent to Kafka topic: {}",
                orderResponse
        );
        
        return orderResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long id) {
        return orderRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));

        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        return mapToResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getCustomerName(),
                order.getOrderDate(),
                order.getStatus(),
                order.getItems().stream()
                        .map(item -> new OrderItemResponse(
                                item.getId(),
                                item.getProductName(),
                                item.getQuantity(),
                                item.getPrice()))
                        .collect(Collectors.toSet()),
                order.getTotalAmount()
        );
    }
} 