package com.example.demo.domain.order.service;

import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;

import java.util.List;

public interface OrderService {
    void classifyOrderItemCategory(Long memberId, Long totalOrderPrice, List<OrderItemRegisterRequest> orderItems);
}
