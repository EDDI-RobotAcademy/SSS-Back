package com.example.demo.domain.products.controller.form;

import com.example.demo.domain.products.entity.ProductDetail;
import com.example.demo.domain.products.service.request.ProductsInfoRequest;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductsRegisterForm {

    private String title;
    private Long price;
    private String content;

    private ProductDetail productDetail;

    public ProductsInfoRequest toProductRegisterRequest() {
        return new ProductsInfoRequest(title, price, content, productDetail);
    }
}
