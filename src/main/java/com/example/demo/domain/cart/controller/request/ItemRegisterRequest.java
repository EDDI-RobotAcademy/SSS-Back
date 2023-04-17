package com.example.demo.domain.cart.controller.request;

import com.example.demo.domain.cart.entity.Order;
import com.example.demo.domain.cart.entity.cartItems.ItemCategoryType;
import com.example.demo.domain.cart.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.products.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ItemRegisterRequest {
    @JsonProperty("category")
    private ItemCategoryType itemCategoryType;

    private Long itemId;

    private Integer quantity;


    public ProductOrderItem toProductOrderItem(Product orderProduct, Order firstOrder) {

        return new ProductOrderItem(this.quantity, orderProduct, firstOrder);
    }
}
