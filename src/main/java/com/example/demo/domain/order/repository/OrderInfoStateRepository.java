package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.OrderInfoState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInfoStateRepository extends JpaRepository<OrderInfoState, Long> {
    OrderInfoState findByOrderInfo_id(Long orderId);
}
