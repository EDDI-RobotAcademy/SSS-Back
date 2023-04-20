package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Address;
import lombok.Getter;

import javax.persistence.*;

public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column(nullable = false)
    private OrderState orderState;

    @Getter
    @Embedded
    private Address address;
}
