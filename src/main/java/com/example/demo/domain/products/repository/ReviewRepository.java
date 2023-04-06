package com.example.demo.domain.products.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.products.entity.Review;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.product p where p.productId = :productId order by r.reviewId desc")
    List<Review> findReviewOnProduct(@Param("productId") Long productId);
}
