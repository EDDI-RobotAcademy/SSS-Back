package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.CartRegisterRequest;
import com.example.demo.domain.order.controller.response.CartItemListResponse;

import java.util.List;

public interface CartService {
    void classifyItemCategory(CartRegisterRequest item);

    List<CartItemListResponse> cartItemList(Long memberId);
}
