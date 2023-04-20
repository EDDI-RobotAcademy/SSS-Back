package com.example.demo.domain.cart.service;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelfSaladReadResponse;

import java.util.List;

public interface CartService {
    Integer classifyItemCategory(CartRegisterRequest item);

    List<CartItemListResponse> cartItemList(Long memberId);

    void modifyCartItemQuantity(CartItemQuantityModifyRequest itemRequest);

    void deleteCartItem(CartItemDeleteRequest itemDelete);

    void deleteCartItemList(List<CartItemDeleteRequest> deleteItemlist);

    void selfSaladCartRegister(SelfSaladCartRegisterForm selfSaladItem);

    Integer checkSelfSaladCartLimit(Long memberId);

    List<SelfSaladReadResponse> readSelfSaladIngredient(Long itemId);

    void modifySelfSaladItem(Long itemId, SelfSaladModifyForm modifyForm);
}
