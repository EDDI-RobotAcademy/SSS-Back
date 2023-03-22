package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeasureType {
    MEASURE1("g"), MEASURE2("개");

    final private String measure;

    public static MeasureType ofType(String measure) {
        return null;
    }

}
