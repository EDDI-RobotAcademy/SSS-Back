package com.example.demo.domain.products.service.request;

import com.example.demo.domain.products.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ProductsRegisterRequest {

    final private String title;
    final private Long price;
    final private String content;

    public Product toProduct () { return new Product(title, price, content); }
}
