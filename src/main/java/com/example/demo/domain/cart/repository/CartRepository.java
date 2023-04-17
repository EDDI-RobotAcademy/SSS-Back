package com.example.demo.domain.cart.repository;

import com.example.demo.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember_memberId(Long memberId);
}
