package com.example.demo.domain.order.service.request;

import com.example.demo.domain.cart.entity.cartItems.ItemCategoryType;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderItemRegisterRequest {
    @JsonProperty("category")
    private ItemCategoryType itemCategoryType;

    private Long itemId;

    private Integer quantity;


    public ProductOrderItem toProductOrderItem(Product orderProduct, OrderInfo myOrderInfo) {

        return new ProductOrderItem(this.quantity, orderProduct, myOrderInfo);
    }

    public SideProductOrderItem toSideProductOrderItem(SideProduct sideProduct, OrderInfo myOrderInfo) {
        return new SideProductOrderItem(this.quantity, sideProduct, myOrderInfo);
    }

    public SelfSaladOrderItem toSelfSaladOrderItem(SelfSalad selfSalad, OrderInfo myOrderInfo) {
        return new SelfSaladOrderItem(this.quantity, selfSalad, myOrderInfo);
    }
}
