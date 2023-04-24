package com.example.demo.domain.cart.controller.request;

import com.example.demo.domain.utility.itemCategory.ItemCategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemQuantityModifyRequest {

    @JsonProperty("category")
    private ItemCategoryType itemCategoryType;

    final private Long itemId;

    final private Integer quantity;

}
