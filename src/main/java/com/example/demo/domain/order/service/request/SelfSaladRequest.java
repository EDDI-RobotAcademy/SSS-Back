package com.example.demo.domain.order.service.request;

import com.example.demo.domain.selfSalad.entity.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SelfSaladRequest {
    // 리스트 : 재료 id, 재료 선택 수량, 측정타입
    // 양파 - 30 - g

    final private Long ingredientId;

    final private Long selectedAmount;

    @JsonProperty("amount")
    final private AmountType amountType;


    public SelfSaladIngredient toSelfSaladIngredient(SelfSalad selfSalad, Ingredient ingredient, Amount amount){
        return new SelfSaladIngredient(
                                        selfSalad, ingredient, selectedAmount,amount);
    }

}
