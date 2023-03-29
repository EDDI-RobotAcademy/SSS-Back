package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.controller.form.ProductReadResponse;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.service.request.ProductsRegisterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductsService {
    List<Product> list();

    void register(List<MultipartFile> productImgList, ProductsRegisterRequest request);

    ProductReadResponse read(Long productId);

    List<ProductImgResponse> findProductImage(Long productId);
}
