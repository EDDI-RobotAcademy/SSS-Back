package com.example.demo.domain.sideProducts.controller;

import com.example.demo.domain.sideProducts.dto.request.SideProductRequest;
import com.example.demo.domain.sideProducts.dto.response.SideProductResponse;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.service.SideProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sideproduct")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@RequiredArgsConstructor
public class SideProductsController {

    final private SideProductsService sideProductsService;

    // 등록
    @PostMapping("/register")
    public SideProduct SideProductRegister (@RequestBody SideProductRequest sideProductRequest) {
        log.info("SideProductRegister()");

        return sideProductsService.register(sideProductRequest);
    }

    @GetMapping("/list")
    public List<SideProduct> sideProductList(){
        log.info("sideProductList()");


        return sideProductsService.list();
    }
    @GetMapping("/read/{productId}")
    public SideProduct sideProductRead(@PathVariable("productId") Long productId){
        log.info("sideProductRead()");

        return sideProductsService.read(productId);
    }

    // 삭제
    @DeleteMapping("/{productId}")
    public void sideProductRemove(@PathVariable("productId") Long productId) {
        log.info("sideProductRemove()");

        sideProductsService.remove(productId);
    }


}
