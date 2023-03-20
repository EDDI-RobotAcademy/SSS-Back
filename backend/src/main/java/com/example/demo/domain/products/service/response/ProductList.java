package com.example.demo.domain.products.service.response;

import com.example.demo.domain.products.entity.ProductInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class ProductList {

    private Long productId;

    private String title;

    private Long price;

    private ProductInfo productInfo;


    public ProductList(Long productId, Long price, String title, ProductInfo productInfo) {
    }
}
