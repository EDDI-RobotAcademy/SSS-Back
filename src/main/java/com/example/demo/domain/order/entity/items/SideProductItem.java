package com.example.demo.domain.order.entity.items;

import com.example.demo.domain.order.entity.SideProductCart;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "side_product_cart_id")
    private SideProductCart sideProductCart;

    public SideProductItem(int quantity, SideProduct sideProduct, SideProductCart sideProductCart) {
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.sideProductCart = sideProductCart;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }


}
