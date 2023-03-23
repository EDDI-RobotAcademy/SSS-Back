package com.example.demo.domain.selfSalad.Controller.request;
import lombok.Getter;
@Getter
public class IngredientInfoRequest {

    final private String name;

    // category
    final private String type;

    // amount
    final private Integer max;
    final private Integer min;
    final private Integer unit;
    final private String measure;



    public IngredientInfoRequest(String name, String type,
                                 Integer max, Integer min, Integer unit, String measure) {
        this.name = name;
        this.type = type;
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.measure = measure;
    }
}
