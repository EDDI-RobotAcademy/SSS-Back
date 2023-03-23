package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.entity.convert.MeasureTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measureId;

    /**
     * IngredientAmount.class -- @OneToOne --  Ingredient.class
     */


    /**
     * 관리자가 사용자가 선택할 수 있는 재료의 수량의 최솟값, 최댓값, 단위를 정함
     * unit : 단위
     * max : 수량 최댓값
     * min : 수량 최솟값
     *
     * ex. 10단위로 최소 10g부터 최대 50g
     * ex. 1단위로 최소 1개부터 4개
     */
    @Lob
    private Integer unit;

    @Lob
    private Integer max;

    @Lob
    private Integer min;

    @Column
    private Integer optionCount;

    /**
     * 측정타입 (g/개) enum을 @Convert 후 @Embedded 시킨다.
     */
    @Convert(converter = MeasureTypeConverter.class, attributeName = "measure_type")
    @Column(name = "measure_type", nullable = false)
    @Embedded
    private MeasureType measureType;


    public Amount(Integer max, Integer min, Integer unit, MeasureType measureType) {
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.measureType = measureType;
        this.optionCount = max/unit;
    }
}
