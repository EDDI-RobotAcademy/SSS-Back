package com.example.demo.domain.sideProducts.dto.response;

import com.example.demo.domain.sideProducts.entity.SideProductImg;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SideProductResponse {

    final private Long productId;

    // 내용
    final private String content;

    // 가격
    final private Long price;

    // 제목
    final private String title;

    // 이미지
    final private SideProductImg sideProductImg;



}
