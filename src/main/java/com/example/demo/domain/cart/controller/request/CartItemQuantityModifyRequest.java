package com.example.demo.domain.cart.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemQuantityModifyRequest {

    final private Long itemId;

    final private Integer quantity;

}
