package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.order.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.order.controller.request.*;
import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.controller.response.SelfSaladReadResponse;
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

    @PostMapping(value = "/selfsalad/limit")
    public Integer selfSaladCartCount (@PathVariable("memberId") Long memberId) {
        log.info("checkSelfSaladCartCount()");
        return cartService.checkSelfSaladCartLimit(memberId);
    }
    @PostMapping(value = "/selfsalad/register")
    public void SelfSaladCartRegister (@RequestBody SelfSaladCartRegisterForm selfSaladItem) {
        log.info("cartRegister()");
        cartService.selfSaladCartRegister(selfSaladItem);
    }

    @GetMapping("/list/{memberId}")
    public List<CartItemListResponse> cartItemList(@PathVariable("memberId") Long memberId) {
        log.info("cartItemList()");
        return cartService.cartItemList(memberId);
    }

    @PutMapping("/modify")
    public void cartItemModify(@RequestBody CartItemQuantityModifyRequest itemRequest) {
        log.info("cartItemModify()");
        cartService.modifyCartItemQuantity(itemRequest);
    }

    @GetMapping("/selfsalad/read")
    public List<SelfSaladReadResponse> selfSaladRead(@PathVariable("itemId") Long itemId){
        log.info("selfSaladRead()");
        return cartService.readSelfSaladIngredient(itemId);
    }

    @PutMapping("/selfsalad/modify")
    public void selfSaladItemModify(@PathVariable("itemId") Long itemId,
                                    @RequestBody SelfSaladModifyForm modifyForm) {
        log.info("selfSaladItemModify()");
        cartService.modifySelfSaladItem(itemId, modifyForm);
    }

    @DeleteMapping("/delete")
    public void cartItemRemove(@RequestBody CartItemDeleteRequest itemDelete){
        log.info("cartItemRemove()");

        cartService.deleteCartItem(itemDelete);
    }

}
