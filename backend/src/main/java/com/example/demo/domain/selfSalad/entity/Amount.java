package com.example.demo.domain.selfSalad.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 관리자가 사용자가 선택할 수 있는 재료의 수량의 최솟값, 최댓값, 단위를 정함
     * unit : 단위
     * max : 수량 최댓값
     * min : 수량 최솟값
     *
     * ex. 10단위로 최소 10g부터 최대 50g
     * ex. 1단위로 최소 1개부터 4개
     */
    @Column(nullable = false)
    private Integer unit;

    @Column(nullable = false)
    private Integer max;

    @Column(nullable = false)
    private Integer min;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MeasureType measureType;

    public Amount(MeasureType measureType) {
        this.measureType = measureType;
    }

    public Amount(Integer max, Integer min, Integer unit, MeasureType measureType) {
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.measureType = measureType;
    }
}
