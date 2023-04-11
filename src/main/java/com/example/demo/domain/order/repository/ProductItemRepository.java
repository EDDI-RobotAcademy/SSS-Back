package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.items.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    Optional<ProductItem> findByProduct_productIdAndProductCart_Id(Long productId, Long productCartId);
}
