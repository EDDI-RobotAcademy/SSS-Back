package com.example.demo.domain.cart.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelfSaladModifyRequest {
    final private Long ingredientId;

    final private Long selectedAmount;
}
