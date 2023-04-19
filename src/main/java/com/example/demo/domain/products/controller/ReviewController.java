package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.service.ReviewService;
import com.example.demo.domain.products.service.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    final private ReviewService reviewService;

    @PostMapping("/registerText")
    public void registerText(@RequestBody ReviewRequest request) {
        log.info("reviewText()");
        reviewService.registerText(request);
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void reviewRegister(@RequestPart(value = "file") List<MultipartFile> reviewImgList,
                               @RequestPart(value = "review") ReviewRequest request) {
        log.info("reviewRegister()");

        reviewService.register(reviewImgList, request);
    }

    @GetMapping("/list/{productId}")
    public List<Review> reviewList(@PathVariable("productId") Long productId) {
        log.info("productReviewList()");
        return reviewService.productReviewList(productId);
    }

    @GetMapping("/list-myReview/{memberId}")
    public List<Review> memberReviewList(@PathVariable("memberId") Long memberId) {
        log.info("memberReviewList()");
        return reviewService.memberReviewList(memberId);
    }

    @GetMapping("/image/{reviewId}")
    public List<ReviewImgResponse> reviewImg(@PathVariable("reviewId") Long reviewId) {
        log.info("reviewImage(): " + reviewId);
        return reviewService.reviewImgList(reviewId);
    }

    @PutMapping(value = "/modify/{reviewId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void reviewModify(@PathVariable("reviewId") Long reviewId,
                             @RequestPart(value = "reviewImgList") List<MultipartFile> reviewImgList,
                             @RequestPart(value = "reviewInfo") ReviewRequest request) {
        log.info("reviewModify: " + request + "id: " + reviewId);
        reviewService.modify(reviewId, reviewImgList, request);
    }

    @DeleteMapping("/delete/{reviewId}")
    public void reviewDelete(@PathVariable("reviewId") Long reviewId) {
        reviewService.delete(reviewId);
    }
}
