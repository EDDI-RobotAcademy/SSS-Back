package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.service.ReviewService;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
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

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void reviewRegister(@RequestPart(value = "file") List<MultipartFile> reviewImgList,
                               @RequestPart(value = "review") ReviewRegisterRequest form) {
        log.info("reviewRegister()");

        reviewService.register(reviewImgList, form);
    }

    @GetMapping("/list/{productId}")
    public List<Review> reviewList(@PathVariable("productId") Long productId) {
        log.info("productReviewList()");
        return reviewService.productReviewList(productId);
    }

    @GetMapping("/image/{reviewId}")
    public List<ReviewImgResponse> reviewImg(@PathVariable("reviewId") Long reviewId) {
        log.info("reviewImage(): " + reviewId);
        return reviewService.reviewImgList(reviewId);
    }

}
