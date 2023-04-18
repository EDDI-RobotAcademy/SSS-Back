package com.example.demo.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {

    private Double calorie;
    private Double carbohydrate;
    private Double sugars;
    private Double protein;
    private Double fat;
    private Double aturatedFat;
    private Double natrium;
}
