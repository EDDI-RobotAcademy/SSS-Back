package com.example.demo.domain.cart.controller.response;

import com.example.demo.domain.cart.entity.cartItems.*;
import lombok.*;

import java.util.Date;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CartItemListResponse {
    // 카트 아이템 id, (이미지, 상품명, 가격), 담은 수량

    private Long cartItemId;

    private Integer quantity;

    private Date addedDate;

    private Long productId;

    private String title;

    private String editedImg;

    private Long totalPrice;



    public CartItemListResponse toProductItem(CartItem productItem) {
        return new CartItemListResponse(
                productItem.getId(),
                productItem.getQuantity(),
                productItem.getAddedDate(),
                productItem.getProduct().getProductId(),
                productItem.getProduct().getTitle(),
                productItem.getProduct().getProductImgs().get(0).getEditedImg(),
                productItem.getProduct().getPrice() * productItem.getQuantity());
    }

    public CartItemListResponse toSideProductItem(CartItem sideProductItem) {
        return new CartItemListResponse(
                sideProductItem.getId(),
                sideProductItem.getQuantity(),
                sideProductItem.getAddedDate(),
                sideProductItem.getSideProduct().getSideProductId(),
                sideProductItem.getSideProduct().getTitle(),
                sideProductItem.getSideProduct().getSideProductImg().getEditedImg(),
                sideProductItem.getSideProduct().getPrice() * sideProductItem.getQuantity());
    }

    public CartItemListResponse toSelfSaladItem(CartItem selfSaladItem) {
        return new CartItemListResponse(
                selfSaladItem.getId(),
                selfSaladItem.getQuantity(),
                selfSaladItem.getAddedDate(),
                selfSaladItem.getSelfSalad().getId(),
                selfSaladItem.getSelfSalad().getTitle(),
                "",
                selfSaladItem.getSelfSalad().getTotalPrice() * selfSaladItem.getQuantity());
    }

}
