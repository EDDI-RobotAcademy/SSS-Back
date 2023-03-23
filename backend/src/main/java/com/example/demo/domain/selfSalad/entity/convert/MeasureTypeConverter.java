package com.example.demo.domain.selfSalad.entity.convert;

import com.example.demo.domain.selfSalad.entity.MeasureType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class MeasureTypeConverter implements AttributeConverter<MeasureType, String> {


    @Override
    public String convertToDatabaseColumn(MeasureType attribute) {
        if(attribute == null){
            throw new IllegalArgumentException("단위 입력이 되지 않았습니다.");
        }
        return attribute.getMeasure();
    }

    @Override
    public MeasureType convertToEntityAttribute(String dbData) {
        if( dbData == null || dbData.isBlank()){
            throw new IllegalArgumentException("dbData가 비어있습니다.");
        }
        return MeasureType.ofType(dbData);
    }
}
