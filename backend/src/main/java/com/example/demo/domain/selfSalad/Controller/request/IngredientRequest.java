package com.example.demo.domain.selfSalad.Controller.request;

import lombok.Getter;

@Getter
public class IngredientRequest {
    final private Long id;

    final private String name;

    final private String imagePath;

    // category
    final private String type;

    // amount
    final private Integer max;
    final private Integer min;
    final private Integer unit;
    final private String measure;



    public IngredientRequest(Long id, String name, String imagePath, String type,
                             Integer max, Integer min, Integer unit, String measure) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.type = type;
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.measure = measure;
    }
}
