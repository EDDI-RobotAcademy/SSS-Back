package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.Controller.response.IngredientInfoReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Getter
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

    @OneToOne(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private IngredientImg ingredientImg;

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<IngredientCategory> ingredientCategories = new HashSet<>();

    @OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<IngredientAmount> ingredientAmounts = new HashSet<>();

    /**
     * 재료 등록 (이름, 참조되는 이미지 객체 지정)
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
    public void setName(String modifyName) {
        this.name = modifyName;
    }

}
