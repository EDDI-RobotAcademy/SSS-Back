package com.example.demo.domain.order.entity.items;

import com.example.demo.domain.order.entity.SelfSaladCart;
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
    @GeneratedValue
    private Long id;

    @Column
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_id")
    private SelfSalad selfSalad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_cart_id")
    private SelfSaladCart selfSaladCart;

    @CreationTimestamp
    private Date addedDate;

    public SelfSaladItem(Integer quantity, SelfSaladCart selfSaladCart, SelfSalad selfSalad) {
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.selfSaladCart = selfSaladCart;
    }
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
}
