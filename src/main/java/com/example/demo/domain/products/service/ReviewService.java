package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.service.request.ReviewRequest;
import com.example.demo.domain.products.service.response.ReviewListResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    void registerText(ReviewRequest request);

    void register(List<MultipartFile> reviewImgList, ReviewRequest request) throws IOException;

    List<ReviewListResponse> productReviewList(Long productId);

    List<ReviewListResponse> memberReviewList(Long memberId);

    List<ReviewImgResponse> reviewImgList(Long reviewId);

    void modifyText(Long reviewId, ReviewRequest request);

    void modify(Long reviewId, List<MultipartFile> reviewImgList, ReviewRequest request);

    void delete(Long reviewId);

}
