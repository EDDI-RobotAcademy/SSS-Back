package com.example.demo.domain.selfSalad.service.request;

import com.example.demo.domain.selfSalad.entity.CategoryType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class IngredientInfoModifyRequest {

    final private String name;
    @JsonProperty("categoryType") // vue 에서 받아온 카테고리 데이터
    final private CategoryType categoryType;

    final private String modifyEditedImg;

}
