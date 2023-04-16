package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.order.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.order.controller.request.*;
import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.controller.response.SelfSaladReadResponse;

import java.util.List;

public interface CartService {
    Integer classifyItemCategory(CartRegisterRequest item);

    List<CartItemListResponse> cartItemList(Long memberId);

    void modifyCartItemQuantity(CartItemQuantityModifyRequest itemRequest);

    void deleteCartItem(CartItemDeleteRequest itemDelete);

    void selfSaladCartRegister(SelfSaladCartRegisterForm selfSaladItem);

    Integer checkSelfSaladCartLimit(Long memberId);

    List<SelfSaladReadResponse> readSelfSaladIngredient(Long itemId);
}
