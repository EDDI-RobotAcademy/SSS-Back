package com.example.demo.domain.order.controller.form;

import com.example.demo.domain.order.service.request.SelfSaladRequest;
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

    private List<SelfSaladRequest> selfSaladRequestList;
}
