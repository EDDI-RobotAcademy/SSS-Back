package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.order.controller.request.CartRegisterRequest;
import com.example.demo.domain.order.controller.response.CartItemListResponse;

import java.util.List;

public interface CartService {
    Integer classifyItemCategory(CartRegisterRequest item);

    List<CartItemListResponse> cartItemList(Long memberId);

    void modifyCartItemQuantity(CartItemQuantityModifyRequest itemRequest);

    void deleteCartItem(CartItemDeleteRequest itemDelete);
}
