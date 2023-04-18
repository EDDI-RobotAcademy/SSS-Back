package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SideProductOrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sideProduct_id")
    private SideProduct sideProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_order_id")
    private Order order;

    public SideProductOrderItem(Integer quantity, SideProduct sideProduct, Order order) {
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.order = order;
    }
}
