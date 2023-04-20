package com.example.demo.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderState {

    PAYMENT_COMPLETE("결제완료"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETE("배송완료"),
    PAYMENT_CONFIRM("구매확정"),
    CANCEL("취소"),
    EXCHANGE("교환"),
    REFUND("환불");

    private final String orderState;

}