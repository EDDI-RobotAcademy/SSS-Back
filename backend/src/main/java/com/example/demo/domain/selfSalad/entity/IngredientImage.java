package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class IngredientImage {
    /**
     * filName : 등록 요청시 받아오는 이미지 파일명
     * randomName : 받아온 이미지 파일명을 uuid 로 바꾼 파일명
     */
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
