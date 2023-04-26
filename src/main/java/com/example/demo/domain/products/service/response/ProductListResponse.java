package com.example.demo.domain.products.service.response;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductListResponse {

    final private Long productId;
    final private String title;
    final private Long price;
    final private String content;
    final private int viewCnt;
    final private int favoriteCnt;
    final private List<ProductImgResponse> productImgList;
}
