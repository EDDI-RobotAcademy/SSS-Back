package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.controller.request.SelfSaladCartRegisterForm;
import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.order.controller.request.CartRegisterRequest;
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
    public Integer CartRegister (@RequestBody CartRegisterRequest cartItem) {
        log.info("cartRegister()");
        return cartService.classifyItemCategory(cartItem);
    }

    }

    @GetMapping("/list")
    public List<CartItemListResponse> cartItemList(@PathVariable("memberId") Long memberId) {
        log.info("cartItemList()");
        return cartService.cartItemList(memberId);
    }

    @PutMapping("/modify")
    public void cartItemModify(@RequestBody CartItemQuantityModifyRequest itemRequest) {
        log.info("cartItemModify()");
        cartService.modifyCartItemQuantity(itemRequest);
    }

    @DeleteMapping("/delete")
    public void cartItemRemove(@RequestBody CartItemDeleteRequest itemDelete){
        log.info("cartItemRemove()");

        cartService.deleteCartItem(itemDelete);
    }

}
