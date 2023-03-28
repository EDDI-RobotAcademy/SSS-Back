package com.example.demo.domain.selfSalad.Controller.request;
import com.example.demo.domain.selfSalad.entity.convert.StringToIntegerConverter;
import lombok.Getter;

import javax.persistence.Convert;

@Getter
public class IngredientInfoRequest {

    final private String name;

    // category
    final private String category;

    // amount
    /**
     * AttributeConverter 인터페이스 + @Converter

     * AttributeConverter 인터페이스
     - convertToDatabaseColumn : 입력데이터 > convert > db데이터
     - convertToEntityAttribute : db데이터  > convert > 출력데이터

     - StringToIntegerConverter : String > Integer

     *
     */

    @Convert(converter = StringToIntegerConverter.class, attributeName = "amount_convert")
    final private Integer max;

    @Convert(converter = StringToIntegerConverter.class, attributeName = "amount_convert")
    final private Integer min;

    @Convert(converter = StringToIntegerConverter.class, attributeName = "amount_convert")
    final private Integer unit;

    final private String measure;



    public IngredientInfoRequest(String name, String category,
                                 Integer max, Integer min, Integer unit, String measure) {
        this.name = name;
        this.category = category;
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.measure = measure;
    }
}
