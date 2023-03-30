package com.example.demo.domain.selfSalad.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {
    /**
     * Aggregate Root
     * name : 재료명 (ex. 양파)
     * ingredientImage : 재료 이미지
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private IngredientImg ingredientImg;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private Set<IngredientCategory> ingredientCategories = new HashSet<>();

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
    private Set<IngredientAmount> ingredientAmounts = new HashSet<>();

    /**
     * 재료 등록 (이름, 참조되는 이미지 객체 지정 후 이미지 entity 에 넘기기 = 이미지 레포지터리에 저장하기 위해..?)
     * @param name
     * @param ingredientImg
     */
    public Ingredient(String name, IngredientImg ingredientImg) {

        this.name = name;
        this.ingredientImg = ingredientImg;

        ingredientImg.setIngredient(this);
    }


    /**
     * 재료 이미지 수정
     */
    //public void modifyImage(IngredientImg ingredientImg){ this.ingredientImg = ingredientImg ; }
}
