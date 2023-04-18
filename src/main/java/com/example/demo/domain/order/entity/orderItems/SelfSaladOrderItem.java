package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfSaladOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selfSalad_id")
    private SelfSalad selfSalad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    public SelfSaladOrderItem(Integer quantity, SelfSalad selfSalad, OrderInfo orderInfo) {
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.orderInfo = orderInfo;
    }
}
