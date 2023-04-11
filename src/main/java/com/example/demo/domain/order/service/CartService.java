package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.CartRegisterRequest;

public interface CartService {
    void classifyItemCategory(CartRegisterRequest item);

}
