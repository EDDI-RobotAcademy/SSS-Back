package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.service.request.ProductsInfoRequest;
import com.example.demo.domain.products.service.response.ProductListResponse;
import com.example.demo.domain.products.service.response.ProductReadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductsService {
    List<ProductListResponse> list();

    void register(List<MultipartFile> productImgList, ProductsInfoRequest request);

    ProductReadResponse read(Long productId);

    List<ProductImgResponse> findProductImage(Long productId);

    Product modify(Long productId, List<MultipartFile> productImgList, ProductsInfoRequest request);

    Product modifyWithoutImg(Long productId, ProductsInfoRequest request);

    void delete(Long productId);

    void viewCntUp(Long productId);

    List<Product> listByView();

    List<Product> listByFavorite();
}
