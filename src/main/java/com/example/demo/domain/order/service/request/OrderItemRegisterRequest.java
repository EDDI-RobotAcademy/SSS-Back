package com.example.demo.domain.order.service.request;

import com.example.demo.domain.order.entity.Order;
import com.example.demo.domain.cart.entity.cartItems.ItemCategoryType;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import com.example.demo.domain.products.entity.Product;
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


    public ProductOrderItem toProductOrderItem(Product orderProduct, Order myOrder) {

        return new ProductOrderItem(this.quantity, orderProduct, myOrder);
    }

    public SideProductOrderItem toSideProductOrderItem(SideProduct sideProduct, Order myOrder) {
        return new SideProductOrderItem(this.quantity, sideProduct, myOrder);
    }
}
