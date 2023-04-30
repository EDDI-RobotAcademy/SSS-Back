package com.example.demo.domain.order.controller;

import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.service.OrderInfoService;
import com.example.demo.domain.utility.TokenBasedController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderInfoController extends TokenBasedController {
    final private OrderInfoService orderInfoService;

    @PostMapping(value = "/register")
    public void orderRegister (@RequestBody OrderInfoRegisterForm orderForm, HttpServletRequest requestToken) {
        log.info("orderRegister()");
        Long memberId = findMemberId(requestToken);

        orderInfoService.orderRegister(memberId, orderForm);
    }

    }
