package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProduct_ProductId(Long productId);

    List<Review> findByMember_MemberId(Long memberId);

    void deleteByMember_memberId(Long memberId);
}
