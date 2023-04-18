package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.Order;
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
    @JoinColumn(name = "order_id")
    private Order order;

    public SelfSaladOrderItem(Integer quantity, SelfSalad selfSalad, Order order) {
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.order = order;
    }
}
