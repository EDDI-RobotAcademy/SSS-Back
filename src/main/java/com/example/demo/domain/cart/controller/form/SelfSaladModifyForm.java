package com.example.demo.domain.cart.controller.form;

import com.example.demo.domain.cart.service.request.SelfSaladModifyRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class SelfSaladModifyForm {

    private Long totalPrice;

    private Long totalCalorie;

    private List<SelfSaladModifyRequest> selfSaladModifyRequestList;
}
