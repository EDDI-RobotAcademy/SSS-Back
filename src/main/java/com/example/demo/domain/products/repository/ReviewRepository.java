package com.example.demo.domain.products.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.domain.products.entity.Review;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r join fetch r.product p where p.productId = :productId order by r.reviewId desc")
    List<Review> findByProductId(@Param("productId") Long productId);

//    @Query("select r from Review r join fetch r.orderInfo.payment join fetch r.product join fetch r.member m where m.id= :memberId")
//    List<Review> findByMemberId(Long memberId);
}
