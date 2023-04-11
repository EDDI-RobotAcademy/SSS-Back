package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.order.entity.SideProductCart;
import com.example.demo.domain.order.entity.items.ItemCategoryType;
import com.example.demo.domain.order.entity.items.ProductItem;
import com.example.demo.domain.order.entity.items.SideProductItem;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CartRegisterRequest {

    /**
     * vue 에서 장바구니 아이템을 보낼 때
     * 상품 item 의 카테고리, 번호, 수량을 보냄
     *
     * userToken : 장바구니 담은 회원의 userToken
     *
     */
    @JsonProperty("itemCategoryType")
    private ItemCategoryType itemCategoryType;

    private Long itemId;

    private int quantity;

    private Long memberId;


    public ProductItem toProductItem(Product product, ProductCart productCart){
        return new ProductItem(
                quantity, product, productCart
        );
    }
    public SideProductItem toSideProductItem(SideProduct sideproduct, SideProductCart sideProductCart){
        return new SideProductItem(
                quantity, sideproduct, sideProductCart
        );
    }
}

