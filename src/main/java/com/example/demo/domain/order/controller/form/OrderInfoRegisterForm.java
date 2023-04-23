package com.example.demo.domain.order.controller.form;

import com.example.demo.domain.order.service.request.DeliveryRegisterRequest;
import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import com.example.demo.domain.order.service.request.PaymentRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderInfoRegisterForm {

    // OrderInfo
    private final Long totalOrderPrice; // 총 금액

    // OrderItem
    private final List<OrderItemRegisterRequest> orderItemRegisterRequestList; // 주문 상품 리스트

    // Delivery & Address
    private final DeliveryRegisterRequest deliveryRegisterRequest; // 배송 정보

    // Payment
    private final PaymentRequest paymentRequest; // 결제 정보


}