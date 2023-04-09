package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.ProductImg;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.entity.ReviewImg;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.repository.ReviewImgRepository;
import com.example.demo.domain.products.repository.ReviewRepository;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
import com.example.demo.domain.products.service.request.ReviewRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
            String imgPath = "../SSS-Front/src/assets/review/";

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
// 회원별 자신이 작성한 후기 목록 반환
//    @Override
//    public List<Review> memberReviewList(Long memberId) {
//        List<Review> reviewList = reviewRepository.findByMemberId(memberId);
//        return reviewList;
//    }

    @Override
    public List<ReviewImgResponse> reviewImgList(Long reviewId) {
        List<ReviewImgResponse> reviewImgList = reviewImgRepository.findReviewImgById(reviewId);

        for(ReviewImgResponse reviewImgResponse: reviewImgList) {
            System.out.println("으악: " + reviewImgResponse.getEditedImg());
        }

        return reviewImgList;
    }

    @Override
    public void modify(Long reviewId, List<MultipartFile> reviewImgList, ReviewRequest request) {
        Optional<Review> maybeReview = reviewRepository.findById(reviewId);
        if(maybeReview.isEmpty()) {
            System.out.println("해당 reviewId 정보 없음:  " + reviewId);
        }

        Review review = maybeReview.get();
        List<ReviewImg> imgList = new ArrayList<>();
        List<ReviewImgResponse> removeImgs = reviewImgRepository.findReviewImgById(reviewId);

        final String imgPath = "../SSS-Front/src/assets/review/";
        for(int i = 0; i < removeImgs.size(); i++) {
            String fileName = removeImgs.get(i).getEditedImg();
            System.out.println(fileName);

            File file = new File(imgPath + fileName);

            if (file.exists()) {
                file.delete();
            } else {
                System.out.println("파일 삭제 실패");
            }
        }
        reviewImgRepository.deleteReviewImgById(reviewId);

        review.setRating(request.getRating());
        review.setContent(request.getContent());

        try {
            for (MultipartFile multipartFile: reviewImgList) {
                log.info(multipartFile.getOriginalFilename());

                UUID uuid = UUID.randomUUID();

                String original = multipartFile.getOriginalFilename();
                String edit = uuid + original;

                FileOutputStream writer = new FileOutputStream(imgPath + edit);

                writer.write(multipartFile.getBytes());
                writer.close();

                ReviewImg reviewImg = new ReviewImg();
                reviewImg.setOriginImg(original);
                reviewImg.setEditedImg(edit);
                reviewImg.setReview(review);
                reviewImg.setImgPath(imgPath);
                imgList.add(reviewImg);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        reviewRepository.save(review);
        reviewImgRepository.saveAll(imgList);
    }

    @Override
    public void delete(Long reviewId) {
        List<ReviewImgResponse> removeImgs = reviewImgRepository.findReviewImgById(reviewId);
        final String imgPath = "../SSS-Front/src/assets/review/";

        for(int i = 0; i < removeImgs.size(); i++) {
            String fileName = removeImgs.get(i).getEditedImg();
            System.out.println(fileName);

            File file = new File(imgPath + fileName);

            if(file.exists()) {
                file.delete();
            } else {
                System.out.println("삭제 실패");
            }
        }

        reviewImgRepository.deleteReviewImgById(reviewId);
        reviewRepository.deleteById(reviewId);
    }
}
