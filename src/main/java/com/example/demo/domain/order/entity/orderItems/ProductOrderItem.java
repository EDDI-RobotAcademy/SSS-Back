package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.products.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductOrderItem {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    public ProductOrderItem(Integer quantity, Product product, OrderInfo orderInfo) {
        this.quantity = quantity;
        this.product = product;
        this.orderInfo = orderInfo;
    }
}
