package com.example.demo.domain.products.service;

import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.entity.ReviewImg;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.repository.ReviewImgRepository;
import com.example.demo.domain.products.repository.ReviewRepository;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    final private ProductsRepository productsRepository;
    final private ReviewRepository reviewRepository;
    final private ReviewImgRepository reviewImgRepository;

//    @Override
//    public void register(ReviewRegisterRequest request) {
//        Review review = new Review();
//
//        review.setWriter(request.getWriter());
//        review.setRating(request.getRating());
//        review.setContent(review.getContent());
//
//        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());
//        if(maybeProduct.isPresent()) {
//            Product product = maybeProduct.get();
//            review.setProduct(product);
//        } else {
//            throw new RuntimeException("등록된 상품이 아닙니다.");
//        }
//    }

    @Override
    public void register(List<MultipartFile> files, ReviewRegisterRequest request) {

        List<ReviewImg> imgList = new ArrayList<>();
        Review review = new Review();

        review.setWriter(request.getWriter());
        review.setRating(request.getRating());
        review.setContent(request.getContent());

        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());
        if(maybeProduct.isPresent()) {
            Product product = maybeProduct.get();
            review.setProduct(product);
        } else {
            throw new RuntimeException("등록된 상품이 아닙니다.");
        }

        for (MultipartFile multipartFile : files) {
            UUID uuid = UUID.randomUUID();
            String originImg = multipartFile.getOriginalFilename();
            String editedImg = uuid + originImg;
            String imgPath = "../SSS-Front/frontend/src/assets/review/";

            ReviewImg reviewImg = new ReviewImg();
            reviewImg.setOriginImg(originImg);
            reviewImg.setEditedImg(editedImg);
            reviewImg.setImgPath(imgPath);
            reviewImg.setReview(review);
            imgList.add(reviewImg);
            log.info(multipartFile.getOriginalFilename());

            try {

                FileOutputStream writer = new FileOutputStream(
                        imgPath + editedImg
                );

                writer.write(multipartFile.getBytes());
                writer.close();
                log.info("file upload success");

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        reviewRepository.save(review);
        reviewImgRepository.saveAll(imgList);
    }

    @Override
    public List<Review> productReviewList(Long productId) {
        List<Review> reviewList = reviewRepository.findByProductId(productId);
        return reviewList;
    }

//    @Override
//    public List<Review> memberReviewList(Long memberId) {
//        List<Review> reviewList = reviewRepository.findByMemberId(memberId);
//        return reviewList;
//    }

    @Override
    public List<ReviewImgResponse> findReviewImg(Long reviewId) {
        List<ReviewImgResponse> reviewImgList = reviewImgRepository.findReviewImgById(reviewId);
        return reviewImgList;
    }
}
