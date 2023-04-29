package com.example.demo.product.review;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.entity.Review;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.products.repository.ReviewImgRepository;
import com.example.demo.domain.products.repository.ReviewRepository;
import com.example.demo.domain.products.service.ReviewService;
import com.example.demo.domain.products.service.request.ReviewRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductReviewTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewImgRepository reviewImgRepository;

    @Test
    void 상품_구매후기_등록_이미지포함() throws IOException {
        List<MultipartFile> fileList = new ArrayList<>() {};
        MockMultipartFile file1 = new MockMultipartFile(
                "image1", "redbeet.jpg", "image/jpg", new FileInputStream("C:/khproj/redbeet.jpg")
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "image2", "romaine.jpg", "image/jpg", new FileInputStream("C:/khproj/romaine.jpg")
        );
        fileList.add(file1);
        fileList.add(file2);


        ReviewRequest request = new ReviewRequest(1L,3L, 5,"리뷰등록사진", 1L);


        System.out.println("file :  " + fileList);
        System.out.println("request :  " + request);

        reviewService.register(fileList, request);
    }

    @Test
    void 후기_사진없이() {
        ReviewRequest request = new ReviewRequest(1L, 4L, 4, "사진없이등록되냐", 1L);
        reviewService.registerText(request);
    }
}
