package com.example.demo.domain.products.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.repository.OrderInfoRepository;
import com.example.demo.domain.products.controller.form.ReviewImgResponse;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.entity.ReviewImg;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.repository.ReviewImgRepository;
import com.example.demo.domain.products.repository.ReviewRepository;
import com.example.demo.domain.products.service.request.ReviewRequest;
import com.example.demo.domain.products.service.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
    final private MemberRepository memberRepository;
    final private ReviewRepository reviewRepository;
    final private ReviewImgRepository reviewImgRepository;
    final private OrderInfoRepository orderInfoRepository;

    @Override
    public void registerText(ReviewRequest request) {
        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());
        Optional<Member> maybeMember = memberRepository.findById(request.getMemberId());
        Optional<OrderInfo> maybeOrderInfo = orderInfoRepository.findById(request.getOrderInfoId());

        if(!maybeProduct.isPresent() || !maybeMember.isPresent()) {
            throw new RuntimeException("등록된 상품이나 회원이 아닙니다.");
        }

        Product product = maybeProduct.get();
        Member member = maybeMember.get();
        OrderInfo orderInfo = maybeOrderInfo.get();

        Review review =  Review.builder()
                            .product(product)
                            .member(member)
                            .rating(request.getRating())
                            .content(request.getContent())
                            .orderInfo(orderInfo)
                            .build();

        // 리뷰 작성 시 구매확정으로..? 아니면 구매 확정을 해야 리뷰를 작성할 수 있게...?
//        orderInfo.setOrderState(OrderState.PAYMENT_CONFIRM);
//        orderInfoRepository.save(orderInfo);

        reviewRepository.save(review);
    }

    @Override
    public void register(List<MultipartFile> files, ReviewRequest request) {

        List<ReviewImg> imgList = new ArrayList<>();

        Optional<Product> maybeProduct = productsRepository.findById(request.getProductId());
        Optional<Member> maybeMember = memberRepository.findById(request.getMemberId());
        Optional<OrderInfo> maybeOrderInfo = orderInfoRepository.findById(request.getOrderInfoId());

        if(!maybeProduct.isPresent()) { // || !maybeMember.isPresent()
            throw new RuntimeException("등록된 상품이나 회원이 아닙니다.");
        }

        Product product = maybeProduct.get();
        Member member = maybeMember.get();
        OrderInfo orderInfo = maybeOrderInfo.get();

        Review review = Review.builder()
                            .product(product)
                            .member(member)
                            .rating(request.getRating())
                            .content(request.getContent())
                            .orderInfo(orderInfo)
                            .build();

        for (MultipartFile multipartFile : files) {
            UUID uuid = UUID.randomUUID();
            String originImg = multipartFile.getOriginalFilename();
            String editedImg = uuid + originImg;
            String imgPath = "../SSS-Front/src/assets/review/";

            ReviewImg reviewImg = ReviewImg.builder()
                    .originImg(originImg)
                    .editedImg(editedImg)
                    .imgPath(imgPath)
                    .review(review)
                    .build();

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

        // 리뷰 작성 시 구매확정으로..? 아니면 구매 확정을 해야 리뷰를 작성할 수 있게...?
//        orderInfo.setOrderState(OrderState.PAYMENT_CONFIRM);
//        orderInfoRepository.save(orderInfo);

        reviewRepository.save(review);
        reviewImgRepository.saveAll(imgList);
    }

    @Override
    @Transactional
    public List<ReviewListResponse> productReviewList(Long productId) {
        Optional<Product> maybeProduct = productsRepository.findById(productId);
        Product product = maybeProduct.get();
        List<Review> reviewList = reviewRepository.findByProduct_ProductId(product.getProductId());

        List<ReviewListResponse> responseList = new ArrayList<>();
        for(Review review : reviewList){
            responseList.add(
                    new ReviewListResponse(
                    review.getReviewId(),
                    review.getProduct().getProductId(),
                    review.getOrderInfo().getMember().getNickname(),
                    review.getReviewImgs(),
                    review.getRating(),
                    review.getContent(),
                    review.getRegDate(),
                    review.getUpdDate())
            );
        }
        return responseList;
    }

    @Override
    @Transactional
    public List<ReviewListResponse> memberReviewList(Long memberId) {
        Optional<Member> maybeMember = memberRepository.findById(memberId);
        Member member = maybeMember.get();
        List<Review> reviewList = reviewRepository.findByMember_MemberId(member.getMemberId());

        List<ReviewListResponse> responseList = new ArrayList<>();
        for(Review review : reviewList){
            responseList.add(
                    new ReviewListResponse(
                            review.getReviewId(),
                            review.getProduct().getProductId(),
                            review.getOrderInfo().getMember().getNickname(),
                            review.getReviewImgs(),
                            review.getRating(),
                            review.getContent(),
                            review.getRegDate(),
                            review.getUpdDate())
            );
        }
        return responseList;

    }


    @Override
    public List<ReviewImgResponse> reviewImgList(Long reviewId) {
        List<ReviewImgResponse> reviewImgList = reviewImgRepository.findReviewImgById(reviewId);

        for(ReviewImgResponse reviewImgResponse: reviewImgList) {
            System.out.println("리뷰이미지: " + reviewImgResponse.getEditedImg());
        }

        return reviewImgList;
    }

    @Override
    public void modifyText(Long reviewId, ReviewRequest request) {
        Optional<Review> maybeReview = reviewRepository.findById(reviewId);
        if(maybeReview.isEmpty()) {
            System.out.println("해당 reviewId 정보 없음:  " + reviewId);
        }

        Review review = maybeReview.get();

        review.setRating(request.getRating());
        review.setContent(request.getContent());

        reviewRepository.save(review);
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

                String originImg = multipartFile.getOriginalFilename();
                String editedImg = uuid + originImg;

                FileOutputStream writer = new FileOutputStream(imgPath + editedImg);

                writer.write(multipartFile.getBytes());
                writer.close();

                ReviewImg reviewImg = ReviewImg.builder()
                        .originImg(originImg)
                        .editedImg(editedImg)
                        .imgPath(imgPath)
                        .review(review)
                        .build();

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
