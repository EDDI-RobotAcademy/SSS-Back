package com.example.demo.domain.products.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
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
