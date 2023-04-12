package com.example.demo.domain.order.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@RequiredArgsConstructor
public class CartItemListResponse {
    // 카트 아이템 id, (이미지, 상품명, 가격), 담은 수량
    final private Long cartItemId;

    final private int quantity;

    final private Date addedDate;

    final private Long productId;

    final private String title;

    final private String editedImg;

    final private Long price;

}
