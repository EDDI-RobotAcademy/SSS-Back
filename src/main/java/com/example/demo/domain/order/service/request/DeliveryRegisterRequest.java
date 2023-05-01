package com.example.demo.domain.order.service.request;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.order.entity.Delivery;
import com.example.demo.domain.order.entity.OrderInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliveryRegisterRequest {

    // 배송정보
    private final Long addressId; // 주소 id (null = 신규주소 등록)

    private final String recipient; //수령인

    private final String deliveryMemo; // 배송 메모

    private final String zipcode;

    private final String city;

    private final String street;

    private final String addressDetail;


    public Delivery toDelivery(Address address, OrderInfo orderInfo){
        return new Delivery(recipient, deliveryMemo, address, orderInfo);
    }
}
