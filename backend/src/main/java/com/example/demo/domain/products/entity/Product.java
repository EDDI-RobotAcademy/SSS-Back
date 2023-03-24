package com.example.demo.domain.products.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long price;

    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private ProductInfo productInfo;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductImg> productImgs = new ArrayList<>();
}
