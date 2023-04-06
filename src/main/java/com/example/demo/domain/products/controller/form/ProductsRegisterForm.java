package com.example.demo.domain.products.controller.form;

import com.example.demo.domain.products.service.request.ProductsInfoRequest;
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

    public ProductsInfoRequest toProductRegisterRequest() {
        return new ProductsInfoRequest(title, price, content);
    }
}
