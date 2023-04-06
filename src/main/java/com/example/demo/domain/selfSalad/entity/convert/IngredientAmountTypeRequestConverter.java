package com.example.demo.domain.selfSalad.entity.convert;

import com.example.demo.domain.selfSalad.entity.AmountType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientAmountTypeRequestConverter implements Converter<String, AmountType> {

    @Override
    public AmountType convert(String ingredientAmountType) {
        return AmountType.valueOf(ingredientAmountType);
    }

}
