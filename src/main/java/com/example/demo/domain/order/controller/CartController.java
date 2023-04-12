package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public List<CartItemListResponse> cartItemList(@PathVariable("memberId") Long memberId) {
        log.info("cartItemList()");
        return cartService.cartItemList(memberId);
    }


}
