package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Category {
    /**
     * categoryName : 재료의 분류 (ex. 채소, 육류...)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;
        //private IngredientType ingredientType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonIgnore
    private List<Ingredient> ingredientList = new ArrayList<>();


    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public void createCategory (Long id, String categoryName){
        Category category = new Category(id, categoryName);
    }

    /**
     * Category 에 해당하는 재료 추가하기
     * @param ingredient
     */
    public void registerIngredient(Ingredient ingredient) {
        this.ingredientList.add(ingredient);
    }
}

