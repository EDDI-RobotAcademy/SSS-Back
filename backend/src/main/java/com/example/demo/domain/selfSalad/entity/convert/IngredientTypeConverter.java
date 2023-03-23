package com.example.demo.domain.selfSalad.entity.convert;

import com.example.demo.domain.selfSalad.entity.IngredientType;

import javax.persistence.AttributeConverter;

//@Converter
public class IngredientTypeConverter implements AttributeConverter<IngredientType, String> {

    /**
     * Entity의 Enum값 <-> DB : 양 방향으로 데이터를 가져올 때 변환하는 방법
     * AttributeConverter 인터페이스 + @Converter

     * AttributeConverter 인터페이스
     - convertToDatabaseColumn : enum > db데이터
     - convertToEntityAttribute : db데이터 > enum
     *
     * @Converter : 엔티티에는 type1, 데이터베이스에는 "과일" 저장
     */

    @Override
    public String convertToDatabaseColumn(IngredientType attribute) {
        if(attribute == null){
            throw new IllegalArgumentException("재료 분류가 필요합니다.");
        }
        return attribute.getType();
    }

    @Override
    public IngredientType convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isBlank()) {
            throw new IllegalArgumentException("dbData가 비어있습니다.");
        }

        return IngredientType.ofType(dbData);
    }


}
