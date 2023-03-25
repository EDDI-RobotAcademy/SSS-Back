package com.example.demo.domain.selfSalad.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IngredientType ingredientType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Ingredient> ingredients = new ArrayList<>();

    public Category(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
}

