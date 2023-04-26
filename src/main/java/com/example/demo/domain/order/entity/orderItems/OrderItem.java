package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Item_Category")
@Entity
public abstract class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderInfo_id")
    OrderInfo orderInfo;

    public OrderItem(Integer quantity, OrderInfo orderInfo) {
        this.quantity = quantity;
        this.orderInfo = orderInfo;
    }

    public Long getId(){
        return this.id;
    }

    public String getOrderItemType() {
        DiscriminatorValue discriminatorValue = this.getClass().getAnnotation(DiscriminatorValue.class);
        return discriminatorValue != null ? discriminatorValue.value() : null;
    }

}