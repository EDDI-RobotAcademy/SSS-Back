package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@DiscriminatorValue("SELF") // 하위클래스
public class SelfSaladOrderItem extends OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_id", nullable = true)
    private SelfSalad selfSalad;


    public SelfSaladOrderItem(Integer quantity, SelfSalad selfSalad, OrderInfo orderInfo) {
        super(quantity, orderInfo);
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.orderInfo = orderInfo;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public SelfSalad getSelfSalad(){
        return this.selfSalad;
    }

    @Override
    public String getOrderItemType() {
        return "SELF";
    }
}
