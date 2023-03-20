package com.example.demo.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnailPath;


    public ProductInfo(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }



}