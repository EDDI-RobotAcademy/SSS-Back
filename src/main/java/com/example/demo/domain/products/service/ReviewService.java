package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
import com.example.demo.domain.products.service.request.ReviewRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    void register(List<MultipartFile> reviewImgList, ReviewRegisterRequest request);

    List<Review> productReviewList(Long productId);

  //  List<Review> memberReviewList(Long memberId);

    List<ReviewImgResponse> reviewImgList(Long reviewId);

    void modify(Long reviewId, List<MultipartFile> reviewImgList, ReviewRequest request);

}
