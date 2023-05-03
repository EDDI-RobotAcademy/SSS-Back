package com.example.demo.domain.cart.controller;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemIdAndCategory;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelectedIngredientsResponse;
import com.example.demo.domain.cart.service.CartService;
import com.example.demo.domain.utility.member.TokenBasedController;
import com.example.demo.domain.utility.itemCategory.ItemCategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController extends TokenBasedController {

    final private CartService cartService;

    @PostMapping(value = "/register")
    public Integer CartRegister (@RequestBody CartRegisterRequest cartItem,
                                 HttpServletRequest requestToken) {
        Long memberId = findMemberIdByToken(requestToken);
        log.info("cartRegister()");
        log.info("memberId"+memberId);
        log.info("cartItem"+cartItem.getItemCategoryType());
        return cartService.classifyItemCategory(memberId,cartItem);
    }

    @GetMapping("/itemInCart")
    public Boolean itemInCart (@RequestBody CartItemIdAndCategory idAndCategory,
                               HttpServletRequest requestToken) {
        Long memberId = findMemberIdByToken(requestToken);
        Long itemId = idAndCategory.getItemId();
        ItemCategoryType itemCategoryType = idAndCategory.getItemCategoryType();
        log.info("isItemInCart()");
        return cartService.isItemInCart(itemId, memberId, itemCategoryType);
    }

    @PostMapping(value = "/selfsalad/limit")
    public Integer selfSaladCartCount (HttpServletRequest requestToken) {
        Long memberId = findMemberIdByToken(requestToken);
        log.info("checkSelfSaladCartCount()");
        return cartService.checkSelfSaladCartLimit(memberId);
    }
    @PostMapping(value = "/selfsalad/register")
    public void SelfSaladCartRegister (@RequestBody SelfSaladCartRegisterForm selfSaladItem,
                                       HttpServletRequest requestToken) {
        Long memberId = findMemberIdByToken(requestToken);
        log.info("cartRegister()");
        cartService.selfSaladCartRegister(memberId, selfSaladItem);
    }

    @GetMapping("/list")
    public List<CartItemListResponse> cartItemList(HttpServletRequest requestToken) {
        Long memberId = findMemberIdByToken(requestToken);
        log.info("cartItemList()");
        return cartService.cartItemList(memberId);
    }

    @PutMapping("/modify")
    public void cartItemModify(@RequestBody CartItemQuantityModifyRequest itemRequest) {
        log.info("cartItemModify()");
        cartService.modifyCartItemQuantity(itemRequest);
    }

    @GetMapping("/selfsalad/read/{itemId}")
    public List<SelectedIngredientsResponse> selfSaladRead(@PathVariable("itemId") Long itemId){
        log.info("selfSaladRead()");
        return cartService.getSelfSaladIngredient(itemId);
    }

    @PutMapping("/selfsalad/modify/{itemId}")
    public void selfSaladItemModify(@PathVariable("itemId") Long itemId,
                                    @RequestBody SelfSaladModifyForm modifyForm) {
        log.info("selfSaladItemModify()");
        cartService.modifySelfSaladItem(itemId, modifyForm);
    }

    @DeleteMapping("/delete")
    public void cartItemRemove(@RequestBody CartItemIdAndCategory deleteItem){
        log.info("cartItemRemove()");
        cartService.deleteCartItem(deleteItem);
    }

    @DeleteMapping("/delete/list")
    public void cartItemRemove(@RequestBody List<CartItemIdAndCategory> deleteItemlist){
        log.info("cartItemRemove()");

        cartService.deleteCartItemList(deleteItemlist);
    }

}
