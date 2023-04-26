package com.example.demo.domain.cart.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SelectedIngredientsResponse {

    // ingredient
    final private Long ingredientId;
    final private String name;
    final private Integer price;


    // IngredientAmount
    final private String amountType;
    final private Integer max;
    final private Integer unit;
    final private Integer calorie;


    // SelectedIngredient in cartItem : selectedAmount
    final private Long selectedAmount;
}
