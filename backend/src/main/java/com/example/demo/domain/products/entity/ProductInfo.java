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

    private String thumbnailPath;


}