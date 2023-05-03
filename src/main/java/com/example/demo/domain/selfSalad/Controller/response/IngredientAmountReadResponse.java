package com.example.demo.domain.selfSalad.Controller.response;

import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.entity.IngredientAmount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class IngredientAmountReadResponse {
    /**
     * 재료 INFO 수정 전의 데이터 반환
     * name : 수정하기 전 이름으로 재료이미지 식별 (변경대상 아님)
     * beforeModifyImg : 수정 대상 이미지명
     */
    private String ingredientName;
    private Integer price;
    private Integer calorie;
    private Integer unit;
    private  Integer max;
    private String amountType;

    public IngredientAmountReadResponse(Ingredient ingredient, IngredientAmount amount) {
        this.ingredientName = ingredient.getName();
        this.price = ingredient.getPrice();
        this.calorie = amount.getCalorie();
        this.unit = amount.getUnit();
        this.max = amount.getMax();
        this.amountType = amount.getAmount().getAmountType().toString();
    }
}
