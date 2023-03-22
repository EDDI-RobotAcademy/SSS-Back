package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.entity.convert.IngredientTypeConverter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Converter 을 필드에 적용할 때는 @Convert
     * ingredientType enum을 @IngredientType에 @Embedded
     */
    @Convert(converter= IngredientTypeConverter.class, attributeName = "ingredient_type")
    @Embedded
    private IngredientType ingredientType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
    // Ingredient.class 의 Category 변수명을 mappedBy에 쓰기
    private List<Ingredient> ingredients = new ArrayList<>();

}

