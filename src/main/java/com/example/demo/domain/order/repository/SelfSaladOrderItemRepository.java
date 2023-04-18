package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfSaladOrderItemRepository extends JpaRepository<SelfSaladOrderItem, Long> {
}
