package com.example.demo.domain.order.controller.form;

import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderInfoRegisterForm {

    private Long memberId;

    private Long totalOrderPrice;

    private List<OrderItemRegisterRequest> orderItemRegisterRequestList;

}