package com.example.demo.domain.cart.controller.request;

import com.example.demo.domain.utility.itemCategory.ItemCategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemIdAndCategory {

    final private Long itemId;

    @JsonProperty("category")
    final private ItemCategoryType itemCategoryType;

}
