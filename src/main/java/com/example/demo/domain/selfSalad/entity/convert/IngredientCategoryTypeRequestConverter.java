package com.example.demo.domain.selfSalad.entity.convert;

import com.example.demo.domain.selfSalad.entity.CategoryType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCategoryTypeRequestConverter implements Converter<String, CategoryType> {

    @Override
    public CategoryType convert(String ingredientCategoryType) {
        return CategoryType.valueOf(ingredientCategoryType);
    }
}
