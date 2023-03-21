package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.service.ProductsService;
import com.example.demo.domain.products.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ProductsController {

    final private ProductsService productsService;
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }


    @GetMapping(path = "/list")
    public List<Product> productsList() {
        return productsService.list();
    }
}
