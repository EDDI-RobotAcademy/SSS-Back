package com.example.demo.domain.cart.controller.request;

import com.example.demo.domain.utility.itemCategory.ItemCategoryType;
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
    @JsonProperty("category")
    private ItemCategoryType itemCategoryType;

    private Long itemId;

    private int quantity;

}

