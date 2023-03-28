package com.example.demo.domain.products.controller.form;

import com.example.demo.domain.products.service.request.ProductsRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductsRegisterForm {

    private String title;
    private Long price;
    private String content;

    public ProductsRegisterRequest toProductRegisterRequest() {
        return new ProductsRegisterRequest(title, price, content);
    }
}
