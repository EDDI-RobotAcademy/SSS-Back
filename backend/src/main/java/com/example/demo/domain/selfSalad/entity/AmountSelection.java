package com.example.demo.domain.selfSalad.entity;

import com.example.demo.domain.selfSalad.entity.MeasureType;

import javax.persistence.*;



@Embeddable
public class AmountSelection {
    /**
     * 관리자가 사용자가 선택할 수 있는 재료의 수량의 최솟값, 최댓값, 단위를 정함
     * unit : 단위
     * max : 수량 최댓값
     * min : 수량 최솟값
     *
     * ex. 10단위로 최소 10g부터 최대 50g
     * ex. 1단위로 최소 1개부터 4개
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long selectionId;
    @Column
    Integer unit;

    @Column
    Integer max;

    @Column
    Integer min;



    /*
    unit, max, min
> 5, 12, 19, 26,

> option 갯수 : max/unit
> option 최솟값 : min
> option 최댓값 : max

     */



}
