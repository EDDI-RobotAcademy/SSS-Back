package com.example.demo.domain.cart.repository;

import com.example.demo.domain.cart.entity.cartItems.SelfSaladItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfSaladItemRepository extends JpaRepository<SelfSaladItem, Long> {
    Integer countByCart_id(Long cartId);

    List<SelfSaladItem> findByCart_Member_memberId(Long memberId);

    List<SelfSaladItem> findByIdIn(List<Long> selfSaladItems);
}
