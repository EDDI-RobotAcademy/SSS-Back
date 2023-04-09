package com.example.demo.domain.selfSalad.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {
    /**
     * Aggregate Root
     * name : 재료명 (ex. 양파)
     * price : 재료 단위당 가격 (unit= 20g 일때 1000원)
     * ingredientImage : 재료 이미지
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column // 재료 최소 단위당 가격
    private Integer price;

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
    public Ingredient(String name, Integer price, IngredientImg ingredientImg) {

        this.name = name;
        this.price = price;
        this.ingredientImg = ingredientImg;

        ingredientImg.setIngredient(this);
    }

    /**
     * 재료 이미지 수정
     */
    public void setName(String modifyName) {
        this.name = modifyName;
    }

    public void setPrice(Integer modifyPrice) {
        this.price = modifyPrice;
    }

}
