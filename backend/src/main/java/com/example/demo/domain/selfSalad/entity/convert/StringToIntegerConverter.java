package com.example.demo.domain.selfSalad.entity.convert;

import javax.persistence.AttributeConverter;

public class StringToIntegerConverter implements AttributeConverter<String, Integer> {
    @Override
    public Integer convertToDatabaseColumn(String amount) {
        if(amount == null){
            throw new IllegalArgumentException("수량 입력이 필요합니다.");
        }
        return Integer.valueOf(amount);
    }

    @Override
    public String convertToEntityAttribute(Integer dbData) {
        if(dbData == null) {
        throw new IllegalArgumentException("dbData가 비어있습니다.");
        }
        return null;
    }
}

