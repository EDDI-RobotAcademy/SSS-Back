package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SideProductOrderItemRepository extends JpaRepository<SideProductOrderItem, Long> {
}
