package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SideProductItem {
    /**
     * 사이드 메뉴 주문
     *
     * 고구마주스 - 3개 - 1번카트
     */
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sideProduct_id")
    private SideProduct sideProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @CreationTimestamp
    private Date addedDate;

    public SideProductItem(int quantity, SideProduct sideProduct, Cart cart) {
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.cart = cart;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }


}
