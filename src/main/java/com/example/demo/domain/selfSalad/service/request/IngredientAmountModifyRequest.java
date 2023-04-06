package com.example.demo.domain.selfSalad.service.request;

import com.example.demo.domain.selfSalad.entity.AmountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IngredientAmountModifyRequest {

    //amountType, max, min, unit, price, calorie 수정
    @JsonProperty("amountType")
    final private AmountType amountType;

    final private Integer max;
    final private Integer min;
    final private Integer unit;
    final private Integer price;
    final private Integer calorie;



}
