package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.products.entity.Product;

import javax.persistence.*;
@Entity
@DiscriminatorValue("PRODUCT") // 하위클래스
public class ProductOrderItem  extends OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;


    public ProductOrderItem(Integer quantity, Product product, OrderInfo orderInfo) {
        super(quantity, orderInfo);
        this.quantity = quantity;
        this.product = product;
        this.orderInfo = orderInfo;
    }

    @Override
    public Product getProduct(){
        return this.product;
    }

    @Override
    public String getOrderItemType() {
        return "PRODUCT";
    }
}
