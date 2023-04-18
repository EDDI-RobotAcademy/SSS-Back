package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;
import com.example.demo.domain.order.service.OrderInfoService;
import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderInfoController {
    final private OrderInfoService orderInfoService;

    @PostMapping(value = "/register")
    public void orderItemsRegister (@RequestBody OrderInfoRegisterForm orderRequest) {
        log.info("orderRegister()");
        Long memberId = orderRequest.getMemberId();
        Long totalOrderPrice = orderRequest.getTotalOrderPrice();

        List<OrderItemRegisterRequest> orderItems =
                orderRequest.getOrderItemRegisterRequestList();

        orderInfoService.classifyOrderItemCategory(memberId, totalOrderPrice, orderItems);
    }
}
