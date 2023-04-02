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

    final private String editedName;

    // IngredientAmount
    final private Integer price;
    final private Integer calorie;
    final private Integer max;
    final private Integer min;
    final private Integer unit;

    final private String amountType;


    public IngredientListResponse(Long id, String name, String editedName, String amountType,
                                  Integer max, Integer min, Integer unit, Integer calorie, Integer price) {
        this.id = id;
        this.name = name;
        this.editedName = editedName;
        this.amountType = amountType;
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.price = price;
        this.calorie = calorie;
    }
}


