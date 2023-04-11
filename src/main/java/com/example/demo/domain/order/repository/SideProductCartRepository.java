package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.SideProductCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SideProductCartRepository extends JpaRepository<SideProductCart, Long> {

    Optional<SideProductCart> findByMember_memberId(Long memberId);
}
