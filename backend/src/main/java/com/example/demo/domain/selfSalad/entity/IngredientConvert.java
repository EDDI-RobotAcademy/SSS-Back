package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.entity.utility.IngredientTypeConverter;
import com.example.demo.domain.selfSalad.entity.utility.MeasureTypeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

//@Entity
@NoArgsConstructor
@Embeddable
@Getter
public class IngredientConvert {
    /**
     * final 선언 불가?
     *
     * @Converter 을 필드에 적용할 때는 @Convert
     */

    @Convert(converter= IngredientTypeConverter.class)
    @Column(name = "ingredient_type", nullable = false)
    private IngredientType ingredientType;

    @Convert(converter = MeasureTypeConverter.class)
    @Column(name = "measure_type", nullable = false)
    private MeasureType measureType;
}
