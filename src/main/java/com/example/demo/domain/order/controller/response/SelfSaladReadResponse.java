package com.example.demo.domain.order.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class SelfSaladReadResponse {
    // 장바구니의 셀프샐러드 수정전 샐러드_재료 정보 반환

    final private Long ingredientId;

    final private Long selectedAmount;

}
