package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.service.ProductsService;
import com.example.demo.domain.products.service.response.ProductList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")

public class ProductsController {

    final private ProductsService productsService;
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping(path = "/list")
    public List<ProductList> productsList() {
        return productsService.list();
    }


}
