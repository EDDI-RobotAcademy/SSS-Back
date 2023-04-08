package com.example.demo.domain.selfSalad.Controller.response;

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

    public IngredientAmountReadResponse(String ingredientName, Integer price, Integer calorie,
                                        Integer unit, Integer max, String amountType) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.calorie = calorie;
        this.unit = unit;
        this.max = max;
        this.amountType = amountType;
    }
}
