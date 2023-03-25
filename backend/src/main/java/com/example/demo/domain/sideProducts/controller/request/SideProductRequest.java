package com.example.demo.domain.sideProducts.controller.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SideProductRequest {

    // 내용
    final private String content;

    // 가격
    final private Long price;

    // 제목
    final private String title;
}
