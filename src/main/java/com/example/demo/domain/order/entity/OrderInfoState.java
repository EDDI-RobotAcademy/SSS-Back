package com.example.demo.domain.order.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@ToString(exclude = "orderInfo")
@Getter
public class OrderInfoState {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orderState_id")
    private OrderState orderState;

    public OrderInfoState(OrderInfo orderInfo, OrderState orderState) {
        this.orderInfo = orderInfo;
        this.orderState = orderState;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderInfo orderInfo, OrderState orderState){
        this.orderInfo = orderInfo;
        this.orderState = orderState;
    }
}

