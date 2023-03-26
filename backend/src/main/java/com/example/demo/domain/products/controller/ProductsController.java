package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.ProductsRegisterForm;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class ProductsController {

    final private ProductsService productsService;

    @GetMapping(path = "/list")
    public List<Product> productsList() {
        return productsService.list();
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void productRegister (@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
                                 @RequestPart(value = "productInfo") ProductsRegisterForm form) {
        log.info("productRegister()");

        productsService.register(productImgList, form.toProductRegisterRequest());
    }
}
