package com.example.demo.domain.products.service;

import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.service.response.ProductList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service

public class ProductsServiceImpl implements ProductsService {
    @Autowired
    ProductsRepository productsRepository;
    @Override
    public List<ProductList> list() {
        List<Product> productList = productsRepository.findAll(Sort.by(Sort.Direction.DESC, "productId"));
        List<ProductList> products = new ArrayList<>();


        for (Product product: productList) {
            products.add(new ProductList(
                    product.getProductId(), product.getPrice(),
                    product.getTitle(), product.getProductInfo()
            ));
        }

        return products;
    }
}
