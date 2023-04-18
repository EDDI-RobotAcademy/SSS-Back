package com.example.demo.domain.products.service.request;

import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductsInfoRequest {

    final private String title;
    final private Long price;
    final private String content;

    private ProductDetail productDetail;

    public Product toProduct () {
        return new Product(title, price, content, productDetail);
    }
}
