package com.example.demo.domain.products.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ReviewRequest {

    final private Long memberId;
    final private Long productId;
    final private int rating;
    final private String content;
}
