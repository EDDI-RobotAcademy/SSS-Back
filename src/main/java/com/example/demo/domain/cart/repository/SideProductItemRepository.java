package com.example.demo.domain.cart.repository;

import com.example.demo.domain.cart.entity.cartItems.SideProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SideProductItemRepository extends JpaRepository<SideProductItem, Long> {
    Optional<SideProductItem> findBySideProduct_sideProductIdAndCart_Id(Long productId, Long productCartId);
    List<SideProductItem> findByCart_Member_memberId(Long memberId);
}
