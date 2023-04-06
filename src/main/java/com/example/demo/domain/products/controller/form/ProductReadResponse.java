package com.example.demo.domain.products.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ProductReadResponse {

    private Long productId;
    private String title;
    private String content;
    private Long price;
}
