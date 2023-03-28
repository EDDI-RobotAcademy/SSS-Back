package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.controller.form.ProductReadResponse;
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

    @GetMapping("/{productId}")
    public ProductReadResponse productRead(@PathVariable("productId") Long productId) {
        log.info("productRead()");

        return productsService.read(productId);
    }

    @GetMapping("/imageList/{productId}")
    public List<ProductImgResponse> readProductImgResource(
            @PathVariable("productId") Long productId) {

        log.info("readProductImgResource(): " + productId);

        return productsService.findProductImage(productId);
    }

}
