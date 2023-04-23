package com.example.demo.domain.cart.repository;

import com.example.demo.domain.cart.entity.cartItems.CartItem;
import com.example.demo.domain.cart.entity.cartItems.SelfSaladItem;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT c FROM CartItem c WHERE c.product.id = :productId AND c.cart.id = :cartId")
    Optional<CartItem> findByProduct_IdAndCart_Id(@Param("productId") Long productId,@Param("cartId") Long cartId);

    @Query("SELECT c FROM CartItem c WHERE c.sideProduct.id = :sideProductId AND c.cart.id = :cartId")
    Optional<CartItem> findBySideProduct_IdAndCart_Id(@Param("sideProductId") Long sideProductId, @Param("cartId") Long cartId);

    List<SelfSaladItem> findByIdIn(List<Long> selfSaladItems);

    Optional<List<CartItem>> findByCart_Member_memberId(Long memberId);

    //@Query("SELECT COUNT(c) FROM CartItem c WHERE TYPE(c) = Self AND c.cart.id = :cartId")
    Optional<Integer> countSelfSaladItemsByCartId( Long cartId);

}
