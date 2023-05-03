package com.example.demo.domain.order.service.request;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DeliveryAddressRequest {

    private final String zipcode;

    private final String city;

    private final String street;

    private final String addressDetail;


    //새로운 배송지 주소 생성 (기본 주소 X)
    public Address toAddress (Member member){
        return new Address( zipcode, city, street,
                addressDetail, member, 'N'
        );

    }
}