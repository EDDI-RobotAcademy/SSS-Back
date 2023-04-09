package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.ReviewImg;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ReviewImgRepository extends JpaRepository<ReviewImg, Long> {

    @Query("select ri.reviewImgId as reviewImgId, ri.editedImg as editedImg from ReviewImg ri join ri.review r where r.reviewId = :reviewId")
    List<ReviewImgResponse> findReviewImgById(@Param("reviewId") Long reviewId);

    @Modifying
    @Transactional
    @Query("delete from ReviewImg ri where ri.review.reviewId = :reviewId")
    void deleteReviewImgById(@Param("reviewId") Long reviewId);
}
