package com.example.demo.domain.order.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderItemListResponse {

    final private Long orderItemId;

    final private Integer quantity;

    final private String title;

    final private Long perId;
}
