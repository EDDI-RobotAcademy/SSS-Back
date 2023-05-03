package com.example.demo.domain.selfSalad.Controller.response;

import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.entity.IngredientAmount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IngredientListResponse {

    // ingredient
    final private Long id;
    final private String name;

    final private Integer price;

    final private String editedImg;

    // IngredientAmount
    final private String amountType;
    final private Integer max;
    final private Integer unit;

    final private Integer calorie;

    public IngredientListResponse(Ingredient ingredient, IngredientAmount amount){
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.price = ingredient.getPrice();
        this.editedImg = ingredient.getIngredientImg().getEditedImg();
        this.amountType = amount.getAmount().getAmountType().toString();
        this.max = amount.getMax();
        this.unit = amount.getUnit();
        this.calorie = amount.getCalorie();
    }

}


