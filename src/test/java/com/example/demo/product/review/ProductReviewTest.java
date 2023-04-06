package com.example.demo.product.review;

import com.example.demo.domain.products.repository.ReviewImgRepository;
import com.example.demo.domain.products.repository.ReviewRepository;
import com.example.demo.domain.products.service.ReviewService;
import com.example.demo.domain.products.service.request.ReviewRegisterRequest;
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

//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
//    private ReviewImgRepository reviewImgRepository;

    @Test
    void 상품_구매후기_등록() throws IOException {
        List<MultipartFile> fileList = new ArrayList<>() {};
        MockMultipartFile file1 = new MockMultipartFile(
                "image1", "ss.png", "image/png", new FileInputStream("C:/khproj/ss.png")
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "image2", "sss.png", "image/png", new FileInputStream("C:/khproj/sss.png")
        );
        fileList.add(file1);
        fileList.add(file2);

        ReviewRegisterRequest request = new ReviewRegisterRequest(
                1L, "yoonji", 5, "리뷰등록테스트");


        System.out.println("file :  " + fileList);
        System.out.println("request :  " + request);

        reviewService.register(fileList, request);
    }
}
