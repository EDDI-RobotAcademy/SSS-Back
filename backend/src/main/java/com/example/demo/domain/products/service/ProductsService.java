package com.example.demo.domain.products.service;

import com.example.demo.domain.products.service.response.ProductList;

import java.util.List;

public interface ProductsService {
    List<ProductList> list();
}
