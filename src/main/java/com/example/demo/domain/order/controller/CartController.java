package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    final private CartService cartService;

    @PostMapping(value = "/register")
    public void productRegister (@RequestBody CartRegisterRequest cartItem) {
        log.info("cartRegister()");
        cartService.classifyItemCategory(cartItem);
    }


}
