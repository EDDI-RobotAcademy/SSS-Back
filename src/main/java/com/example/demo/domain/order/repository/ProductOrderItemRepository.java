package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItem, Long> {
}
