package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCartRepository extends JpaRepository<ProductCart, Long> {
    Optional<ProductCart> findByMember_memberId(Long memberId);
}
