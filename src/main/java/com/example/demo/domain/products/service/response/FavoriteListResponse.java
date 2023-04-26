package com.example.demo.domain.products.service.response;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class FavoriteListResponse {
    final private Long favoriteId;
    final private boolean isLike;
    final private Long productId;
    final private String title;
    final private Long price;
    final List<ProductImgResponse> productImgList;
    final private Long memberId;
}
