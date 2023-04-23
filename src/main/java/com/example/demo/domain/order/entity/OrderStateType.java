package com.example.demo.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderStateType {

    PAYMENT_COMPLETE("결제완료"), PAYMENT_CONFIRM("구매확정"),

    READY_FOR_ITEM("상품준비"), DELIVERY_ONGOING("배송중"), DELIVERY_COMPLETE("배송완료"),

    ABLE_WRITE_REVIEW("리뷰작성 가능"), PART_WRITE_REVIEW("일부 리뷰작성 완료"), WRITE_REVIEW("리뷰작성 완료"),

    REFUND_REQUEST("반품요청"), PAYMENT_REFUND("환불완료"),
    EXCHANGE_REQUEST("교환요청"), EXCHANGE_COMPLETE("교환완료");

    public static OrderStateType valueOfOrderState(String state) {
        return Arrays.stream(values())
                .filter(value -> value.orderState.equals(state))
                .findAny()
                .orElse(null);
    }

    private final String orderState;

}
