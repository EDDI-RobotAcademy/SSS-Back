package com.example.demo.domain.order.controller.request;

import com.example.demo.domain.order.entity.items.ItemCategoryType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemQuantityModifyRequest {

    final private Long itemId;

    final private Integer quantity;

    @JsonProperty("category")
    final private ItemCategoryType itemCategoryType;

}
