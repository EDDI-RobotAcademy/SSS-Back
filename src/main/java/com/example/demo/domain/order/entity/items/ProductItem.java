package com.example.demo.domain.order.entity.items;

import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.products.entity.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductItem {
    /**
     * 완제품 메뉴 주문
     *
     * 고구마샐러드 - 3개 - 1번카트
     */

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_cart_id")
    private ProductCart productCart;

    @CreationTimestamp
    private Date addedDate;

    public ProductItem(Integer quantity, Product product, ProductCart productCart) {
        this.quantity = quantity;
        this.product = product;
        this.productCart = productCart;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
}
