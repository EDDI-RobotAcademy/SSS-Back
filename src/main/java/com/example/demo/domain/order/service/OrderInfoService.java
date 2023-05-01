package com.example.demo.domain.order.service;

import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;
import com.example.demo.domain.order.controller.response.OrderInfoListResponse;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.entity.OrderStateType;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderInfoService {
    void orderRegister(Long memberId, OrderInfoRegisterForm orderForm);
    Boolean updateOrderState(Long orderId, OrderStateType orderStateType);

    @Transactional
    List<OrderInfoListResponse> orderInfoListResponse(Long memberId);
}
