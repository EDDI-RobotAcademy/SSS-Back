package com.example.demo.domain.cart.service;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemIdAndCategory;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelfSaladReadResponse;
import com.example.demo.domain.utility.itemCategory.ItemCategoryType;

import java.util.List;

public interface CartService {
    Integer classifyItemCategory(Long memberId, CartRegisterRequest item);

    Boolean isItemInCart(Long itemId, Long memberId, ItemCategoryType itemCategoryType);

    List<CartItemListResponse> cartItemList(Long memberId);

    void modifyCartItemQuantity(CartItemQuantityModifyRequest itemRequest);

    void deleteCartItem(Long itemDelete);

    void deleteCartItemList(List<CartItemIdAndCategory> deleteItemlist);

    void selfSaladCartRegister(Long memberId, SelfSaladCartRegisterForm selfSaladItem);

    Integer checkSelfSaladCartLimit(Long memberId);

    List<SelfSaladReadResponse> readSelfSaladIngredient(Long itemId);

    void modifySelfSaladItem(Long itemId, SelfSaladModifyForm modifyForm);
}
