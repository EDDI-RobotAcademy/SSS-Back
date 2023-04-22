package com.example.demo.domain.cart.controller.form;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.cart.entity.cartItems.SelfSaladItem;
import com.example.demo.domain.cart.service.request.SelfSaladRequest;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SelfSaladCartRegisterForm {

    // 사용자가 등록한 상품명 , selfSalad 수량 , 총가격, 총칼로리 , 회원 id
    // "내가 만든 샐러드", 3개 , 2만원 , 300 칼로리 , memberId

    private String title;

    private Integer quantity;

    private Long totalPrice;

    private Long totalCalorie;

    private List<SelfSaladRequest> selfSaladRequestList;


    public SelfSalad toSelfSalad(){
        return new SelfSalad(title, totalPrice, totalCalorie);
    }


    public SelfSaladItem toSelfSaladItem (Cart myCart, SelfSalad selfSalad){
        return new SelfSaladItem(quantity, myCart, selfSalad);
    }

}

