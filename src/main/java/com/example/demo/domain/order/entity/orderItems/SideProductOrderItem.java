package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
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
    @JoinColumn(name = "orderInfo_id")
    private OrderInfo orderInfo;

    public SideProductOrderItem(Integer quantity, SideProduct sideProduct, OrderInfo orderInfo) {
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.orderInfo = orderInfo;
    }
}
