package com.example.demo.domain.cart.entity;

import com.example.demo.domain.cart.entity.cartItems.ProductItem;
import com.example.demo.domain.cart.entity.cartItems.SelfSaladItem;
import com.example.demo.domain.cart.entity.cartItems.SideProductItem;
import com.example.demo.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Cart {
    /**
     * Order : 주문 테이블
     *
     * productItem : 완제품 메뉴 (수량포함)
     * member : 로그인한 사용자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductItem> productItemList;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SideProductItem> sideProductItemList;

    @OneToMany(mappedBy = "cart",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SelfSaladItem> selfSaladItemList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}

/**
 *     item : Many / Member : One
 1)       고구마 5       ㄱ
 2)       단호박 4       ㄱ
 3)       단호박 12      ㄱ

 */
