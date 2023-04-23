package com.example.demo.domain.order.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "orderState_uq_orderState_name",
                columnNames = {"orderStateType"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStateType orderStateType;

    public OrderState(OrderStateType orderStateType) {
        this.orderStateType = orderStateType;
    }

    public OrderStateType getOrderStateType() {
        return orderStateType;
    }

    public Long getOrderStateId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", stateType=" + orderStateType +
                '}';
    }
}
