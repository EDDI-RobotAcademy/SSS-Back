package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IngredientImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column
    private String fileName;

    @Column
    private String randomName;


    public IngredientImage(String fileName, String randomName, Ingredient ingredient) {
        this.fileName = fileName;
        this.randomName = randomName;
        this.ingredient = ingredient;
    }

    public void registerToIngredient(){
        this.ingredient.registerImage(this);
    }


}
