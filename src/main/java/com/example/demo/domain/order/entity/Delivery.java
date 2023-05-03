package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
@Entity
@Getter
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;

    @Column
    private String recipient;

    @Column
    private String deliveryMemo;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    public Delivery(String recipient, String deliveryMemo,
                    Address address, OrderInfo orderInfo) {
        this.recipient = recipient;
        this.deliveryMemo = deliveryMemo;
        this.address = address;
        this.orderInfo = orderInfo;
    }
}

