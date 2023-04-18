package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SelfSaladItem {
    /**
     * 완제품 메뉴 주문
     *
     * 셀프샐러드 - 3개 - 1번카트
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_id")
    private SelfSalad selfSalad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreationTimestamp
    private Date addedDate;

    public SelfSaladItem(Integer quantity, Cart cart, SelfSalad selfSalad) {
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.cart = cart;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
}
