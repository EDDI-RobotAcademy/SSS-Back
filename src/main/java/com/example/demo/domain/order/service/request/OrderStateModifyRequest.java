package com.example.demo.domain.order.service.request;

import com.example.demo.domain.order.entity.OrderStateType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStateModifyRequest {

    private final Long orderId;

    @JsonProperty("orderState")
    private final OrderStateType orderStateType;
}
