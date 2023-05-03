package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.service.ReviewService;
import com.example.demo.domain.products.service.request.ReviewRequest;
import com.example.demo.domain.products.service.response.ReviewListResponse;
import com.example.demo.domain.utility.common.TokenBasedController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController extends TokenBasedController {

    final private ReviewService reviewService;

    @PostMapping("/registerText")
    public void registerText(@RequestBody ReviewRequest request) {
        log.info("reviewText()");
        reviewService.registerText(request);
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void reviewRegister(@RequestPart(value = "reviewImgList") List<MultipartFile> reviewImgList,
                               @RequestPart(value = "reviewInfo") ReviewRequest request) throws IOException {
        log.info("reviewRegister()");

        reviewService.register(reviewImgList, request);
    }

    @GetMapping("/list/{productId}")
    public List<ReviewListResponse> reviewList(@PathVariable("productId") Long productId) {
        log.info("productReviewList()");
        return reviewService.productReviewList(productId);
    }

    @GetMapping("/list-myReview")
    public List<ReviewListResponse> memberReviewList(HttpServletRequest requestToken) {
        log.info("memberReviewList()");
        Long memberId = findMemberIdByToken(requestToken);
        return reviewService.memberReviewList(memberId);
    }

    @GetMapping("/image/{reviewId}")
    public List<ReviewImgResponse> reviewImg(@PathVariable("reviewId") Long reviewId) {
        log.info("reviewImage(): " + reviewId);
        return reviewService.reviewImgList(reviewId);
    }

    @PutMapping("/modifyText/{reviewId}")
    public void reviewTextModify(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequest request) {
        log.info("reviewTextModify()");
        reviewService.modifyText(reviewId, request);
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
