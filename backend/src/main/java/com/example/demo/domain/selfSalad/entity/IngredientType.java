package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@RequiredArgsConstructor
@Embeddable
public enum IngredientType {
    /**
     * Entity의 Enum값 <-> DB : 양 방향으로 데이터를 가져올 때 변환하는 방법
     * AttributeConverter 인터페이스 + @Converter

     * AttributeConverter 인터페이스
     - convertToDatabaseColumn : enum > db데이터
     - convertToEntityAttribute : db데이터 > enum

     *
     */
    TYPE1("채소"), TYPE2("육류"), TYPE3("토핑");

    final private String type;

    public static IngredientType ofType(String type){return null; }
}
