package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
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
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreationTimestamp
    private Date addedDate;

    public ProductItem(Integer quantity, Product product, Cart cart) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
}
