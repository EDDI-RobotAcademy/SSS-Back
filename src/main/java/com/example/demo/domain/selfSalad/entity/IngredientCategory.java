package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString(exclude = "ingredient")
@Getter
public class IngredientCategory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    public IngredientCategory(Ingredient ingredient, Category category) {
        this.ingredient = ingredient;
        this.category = category;
    }

    public Category getCategory () {
        return category;
    }

    public void setCategory(Ingredient ingredient, Category category){
        this.ingredient = ingredient;
        this.category = category;
    }

}