package com.example.demo.domain.order.controller.response;

import com.example.demo.domain.order.entity.Delivery;
import com.example.demo.domain.order.entity.OrderStateType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
@Getter
@RequiredArgsConstructor
public class OrderInfoListResponse {

    // OrderInfo
    private Long orderId; // 주문 ID

    // Payment
    private String merchant_uid; // 아임포트에서 부여한 결제 거래 고유 ID

    private Long paid_amount; // 결제된 금액

    private Date paid_at; // 결제 일시

    // Delivery
    //private Delivery delivery; // 배달 Id, 수령인, 배달 메모, 배달 주소
    private String recipient;
    private String deliveryMemo;
    private String zipcode;
    private String city;
    private String street;
    private String addressDetail;

    // OrderState
    private OrderStateType orderStateType; // 주문 상태

    // OrderItem (상품명 + 수량)
    private List<OrderItemListResponse> itemResponse;


    public OrderInfoListResponse(Long orderId, String merchant_uid, Long paid_amount, Date paid_at, String recipient,
                                 String deliveryMemo, String zipcode, String city, String street, String addressDetail,
                                 OrderStateType orderStateType, List<OrderItemListResponse> itemResponse) {
        this.orderId = orderId;
        this.merchant_uid = merchant_uid;
        this.paid_amount = paid_amount;
        this.paid_at = paid_at;
        this.recipient = recipient;
        this.deliveryMemo = deliveryMemo;
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
        this.orderStateType = orderStateType;
        this.itemResponse = itemResponse;
    }
}