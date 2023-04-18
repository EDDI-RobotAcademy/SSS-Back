package com.example.demo.domain.cart.repository;

import com.example.demo.domain.cart.entity.cartItems.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    Optional<ProductItem> findByProduct_productIdAndCart_Id(Long productId, Long productCartId);

    List<ProductItem> findByCart_Member_memberId(Long memberId);

}
