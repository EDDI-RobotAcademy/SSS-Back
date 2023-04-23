package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;

import java.util.List;

public interface OrderInfoService {
    void orderRegister(Long memberId, OrderInfoRegisterForm orderForm);
}
