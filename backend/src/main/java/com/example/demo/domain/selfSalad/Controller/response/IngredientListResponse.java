package com.example.demo.domain.selfSalad.Controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class IngredientListResponse {

    // ingredient
    final private Long id;
    final private String name;

    final private String editedImg;

    // IngredientAmount
    final private String amountType;
    final private Integer max;
    final private Integer min;
    final private Integer unit;

    final private Integer price;
    final private Integer calorie;

}


