package com.example.demo.domain.order.repository;

import com.example.demo.domain.order.entity.items.SelfSaladItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelfSaladItemRepository extends JpaRepository<SelfSaladItem, Long> {
    Integer countBySelfSaladCart_id(Long cartId);

    List<SelfSaladItem> findBySelfSaladCart_Member_memberId(Long memberId);
}
