package com.example.demo.domain.order.controller.response;

import com.example.demo.domain.order.entity.items.ItemCategoryType;
import com.example.demo.domain.order.entity.items.ProductItem;
import com.example.demo.domain.order.entity.items.SelfSaladItem;
import com.example.demo.domain.order.entity.items.SideProductItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@RequiredArgsConstructor
public class CartItemListResponse {
    // 카트 아이템 id, (이미지, 상품명, 가격), 담은 수량
    final private ItemCategoryType category;
    final private Long cartItemId;

    final private Integer quantity;

    final private Date addedDate;

    final private Long productId;

    final private String title;

    final private String editedImg;

    final private Long totalPrice;

    public CartItemListResponse(ItemCategoryType category, Long cartItemId, Integer quantity,
                                Date addedDate, Long productId, String title, String editedImg, Long totalPrice) {
        this.category = category;
        this.cartItemId = cartItemId;
        this.quantity = quantity;
        this.addedDate = addedDate;
        this.productId = productId;
        this.title = title;
        this.editedImg = editedImg;
        this.totalPrice = totalPrice;
    }

    public CartItemListResponse(ProductItem productItem) {
        this(ItemCategoryType.PRODUCT,
                productItem.getId(),
                productItem.getQuantity(),
                productItem.getAddedDate(),
                productItem.getProduct().getProductId(),
                productItem.getProduct().getTitle(),
                productItem.getProduct().getProductImgs().get(0).getEditedImg(),
                productItem.getProduct().getPrice() * productItem.getQuantity());
    }

    public CartItemListResponse(SideProductItem sideProductItem) {
        this(ItemCategoryType.SIDE,
                sideProductItem.getId(),
                sideProductItem.getQuantity(),
                sideProductItem.getAddedDate(),
                sideProductItem.getSideProduct().getSideProductId(),
                sideProductItem.getSideProduct().getTitle(),
                sideProductItem.getSideProduct().getSideProductImg().getEditedImg(),
                sideProductItem.getSideProduct().getPrice() * sideProductItem.getQuantity());
    }

    public CartItemListResponse(SelfSaladItem selfSaladItem) {
        this(ItemCategoryType.SELF_SALAD,
                selfSaladItem.getId(),
                selfSaladItem.getQuantity(),
                selfSaladItem.getAddedDate(),
                selfSaladItem.getSelfSalad().getId(),
                selfSaladItem.getSelfSalad().getTitle(),
                "",
                selfSaladItem.getSelfSalad().getTotalPrice() * selfSaladItem.getQuantity());
    }

}
