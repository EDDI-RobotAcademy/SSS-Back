package com.example.demo.domain.products.service.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteInfoRequest {

    private Long memberId;
    private Long productId;
}
