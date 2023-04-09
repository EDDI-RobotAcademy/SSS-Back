package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.entity.ReviewImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    @Modifying
    @Transactional
    @Query("delete from ReviewImg ri where ri.review.reviewId = :reviewId")
    void deleteReviewImgById(@Param("reviewId") Long reviewId);
}
