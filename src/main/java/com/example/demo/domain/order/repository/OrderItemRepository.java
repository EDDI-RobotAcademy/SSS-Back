package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.orderItems.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
