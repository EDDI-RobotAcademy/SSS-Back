package com.example.demo.domain.products.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Lob
    private String content;

    @Column(nullable = false)
    private int viewCnt = 0;

    @Column(nullable = false)
    private int favoriteCnt = 0;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true) //LAZY 오류 - failed to lazily initialize a collection of role

    @Embedded
    private ProductDetail productDetail;

    public Product(String title, Long price, String content, ProductDetail productDetail) {
        this.title = title;
        this.price = price;
        this.content = content;
        this.productDetail = productDetail;
    }

    public void updateViewCnt() {
        this.viewCnt += 1;
    }
}
