package com.example.demo.domain.products.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Embedded
    private ProductInfo productInfo;


}
