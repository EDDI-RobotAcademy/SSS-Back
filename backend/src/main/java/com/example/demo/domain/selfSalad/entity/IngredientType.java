package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum IngredientType {

    VEGETABLE("채소"), MEAT("육류"), TOPPING("토핑");

    final private String type;


    public static IngredientType valueOfTypeName(String type) {
        return Arrays.stream(values())
                .filter(value -> value.type.equals(type))
                .findAny()
                .orElse(null);
    }
}