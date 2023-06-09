package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.controller.form.ProductsRegisterForm;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.service.ProductsService;
import com.example.demo.domain.products.service.request.ProductsInfoRequest;
import com.example.demo.domain.products.service.response.ProductListResponse;
import com.example.demo.domain.products.service.response.ProductReadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    final private ProductsService productsService;

    @GetMapping(path = "/list")
    public List<ProductListResponse> productsList() {
        return productsService.list();
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void productRegister (@RequestPart(value = "productImgList") List<MultipartFile> productImgList,
                                 @RequestPart(value = "productInfo") ProductsRegisterForm form) throws IOException {
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

    @PutMapping(value = "/modify/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Product productModify(@PathVariable("productId") Long productId,
                                 @RequestPart(value = "productImgList") List<MultipartFile> productImgList,
                                 @RequestPart(value = "productInfo") ProductsInfoRequest request) {
        log.info("productModify: " + request + "id: " + productId);
        return productsService.modify(productId, productImgList, request);
    }

    @PutMapping("/modify-text/{productId}")
    public Product productModifyWithoutImg(@PathVariable("productId") Long productId, @RequestBody ProductsInfoRequest request) {
        log.info(("productModify: " + request + "id: " + productId));
        return productsService.modifyWithoutImg(productId, request);
    }

    @DeleteMapping("/delete/{productId}")
    public void productDelete(@PathVariable("productId") Long productId) {
        productsService.delete(productId);
    }

    @PostMapping("/viewUp/{productId}")
    public void viewCntUp(@PathVariable("productId") Long productId) {
        log.info("viewUp()");
        productsService.viewCntUp(productId);
    }

    @PostMapping("/list/view")
    public List<ProductListResponse> listByView() {
        return productsService.listByView();
    }

    @PostMapping("/list/favorite")
    public List<ProductListResponse> listByFavorite() { return productsService.listByFavorite(); }
}
