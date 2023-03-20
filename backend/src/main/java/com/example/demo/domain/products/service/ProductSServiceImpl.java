package com.example.demo.domain.products.service;

import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductSServiceImpl implements ProductsService {
    final private ProductsRepository productsRepository;

    public ProductSServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Product> list() {
        return productsRepository.findAll(Sort.by(Sort.Direction.DESC, "productId"));
    }
}
