package com.example.demo.domain.sideProducts.controller;

import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.service.SideProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/sideproduct")
@RequiredArgsConstructor
public class SideProductsController {

    final private SideProductsService sideProductsService;

    // 등록
    @PostMapping(value = "/register",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public void SideProductRegister(
            @RequestPart(value = "fileList",required = false) MultipartFile sideProductImgList,
            @RequestPart(value = "productInfo") SideProductRequest sideProductRequest) {
        log.info("productRegister()");
        System.out.println("@@@@@@@@@@@@@@@@@@@");

        sideProductsService.register(sideProductImgList, sideProductRequest);
    }

    @GetMapping("/list")
    public List<SideProduct> sideProductList(){
        log.info("sideProductList()");


        return sideProductsService.list();
    }
    // 상세페이지(읽기)
    @GetMapping("/read/{productId}")
    public SideProductResponse sideProductRead(@PathVariable("productId") Long productId){
        log.info("sideProductRead()");

        return sideProductsService.read(productId);
    }

    // 삭제
    @DeleteMapping("/{productId}")
    public void sideProductRemove(@PathVariable("productId") Long productId) {
        log.info("sideProductRemove()");

        sideProductsService.remove(productId);
    }

    // 수정
    @PutMapping("/modify/{productId}")
    public SideProductResponse sideProductModify(@PathVariable("productId")Long productId,
                                                 @RequestBody SideProductRequest sideProductRequest) {
        log.info("sideProductModify(): " + sideProductRequest + "id: " + productId);

        return sideProductsService.modify(productId, sideProductRequest);
    }


}
