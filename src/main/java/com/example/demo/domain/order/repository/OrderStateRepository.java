package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.OrderState;
import com.example.demo.domain.order.entity.OrderStateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStateRepository extends JpaRepository<OrderState, Long> {
    OrderState findByOrderStateType(OrderStateType orderStateType);
}
