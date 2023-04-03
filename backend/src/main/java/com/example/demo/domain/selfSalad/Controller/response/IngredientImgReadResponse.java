package com.example.demo.domain.selfSalad.Controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class IngredientImgReadResponse {
    /**
     * 이미지 수정 전 요청 정보
     * name : 수정하기 전 이름으로 재료이미지 식별 (변경대상 아님)
     * beforeModifyImg : 수정 대상 이미지명
     */

    private String name;

    private String beforeModifyImg;


    public IngredientImgReadResponse(String name, String beforeModifyImg) {
        this.name = name;
        this.beforeModifyImg = beforeModifyImg;

    }

}
