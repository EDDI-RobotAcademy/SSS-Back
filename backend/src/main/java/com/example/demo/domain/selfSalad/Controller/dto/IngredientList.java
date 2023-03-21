package com.example.demo.domain.selfSalad.Controller.dto;

import com.example.demo.domain.selfSalad.entity.ImageResource;
import lombok.Getter;

@Getter
public class IngredientList {

    private Long ingredientId;
    private String name;
    private ImageResource imageResource;


    public IngredientList(Long ingredientId, String name, ImageResource imageResource) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.imageResource = imageResource;
    }
}
