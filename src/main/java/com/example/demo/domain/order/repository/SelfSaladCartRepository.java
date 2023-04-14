package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.SelfSaladCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SelfSaladCartRepository extends JpaRepository<SelfSaladCart, Long> {
    Optional<SelfSaladCart> findByMember_memberId(Long memberId);
}
