package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.Controller.request.IngredientInfoModifyForm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class IngredientImg {
    /**
     * editedImg : 받아온 이미지 파일명을 uuid 로 바꾼 파일명
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column
    private String editedImg;

    public IngredientImg(String editedImg) {
        this.editedImg = editedImg;
    }

    // 등록 요청
    /**
     * REQUEST 객체 안에서 변경된 UUID 이미지 파일명을 가진 IngredientImg 을 반환
     * @param editedImg
     * @return new IngredientImg
     */
    public static IngredientImg of (String editedImg) {
        return new IngredientImg(editedImg);
    }

    /**
     * 새롭게 등록된 ingredient 를 IngredientImg 에 set 하기
     * @param ingredient
     */
    public void setIngredient (Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getEditedImg(){ return this.editedImg; }

    /**
     * 수정 후 이미지명으로 변경
     * @param modifyImg
     */
    public void setEditedImg(String modifyImg){
        this.editedImg = modifyImg;
    }

}
