package com.example.demo.product;

import com.example.demo.domain.products.service.ProductsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ProductTest {
    @Autowired
    ProductsService productsService;

    @Test
    void 상품_조회수() {
        productsService.viewCntUp(3L);
    }

    @Test
    void 조회수별_목록() {
        System.out.println("조회수순" + productsService.listByView());
    }
}
