package com.example.demo.domain.products.service.request;

import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.ProductDetail;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductsInfoRequest {

    private String title;
    private Long price;
    private String content;

    private ProductDetail productDetail;

    public Product toProduct () {
        return new Product(title, price, content, productDetail);
    }
}
