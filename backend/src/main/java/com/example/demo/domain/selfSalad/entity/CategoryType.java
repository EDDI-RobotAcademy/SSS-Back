package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CategoryType {

    VEGETABLE("채소"), MEAT("육류"), TOPPING("토핑");

    final private String type;

//    public String getType() {
//        return type;
//    }
//
//
//    public static IngredientType valueOfTypeName(String type) {
//        return Arrays.stream(values())
//                .filter(value -> value.type.equals(type))
//                .findAny()
//                .orElse(null);
//    }
}