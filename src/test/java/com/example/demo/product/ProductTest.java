package com.example.demo.product;

import com.example.demo.domain.products.entity.ProductDetail;
import com.example.demo.domain.products.service.ProductsService;
import com.example.demo.domain.products.service.request.ProductsInfoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
public class ProductTest {
    @Autowired
    ProductsService productsService;

    @Test
    void 상품_조회수() {
        productsService.viewCntUp(3L);
    }

    @Test
    void 조회수별_목록() {
        System.out.println("조회수순" + productsService.listByView());
    }

    @Test
    void 영양성분_등록_추가() throws IOException {
        List<MultipartFile> fileList = new ArrayList<>() {};
        MockMultipartFile file1 = new MockMultipartFile(
                "image1", "ss.png", "image/png", new FileInputStream("C:/khproj/ss.png")
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "image2", "sss.png", "image/png", new FileInputStream("C:/khproj/sss.png")
        );
        fileList.add(file1);
        fileList.add(file2);

        ProductDetail productDetail = new ProductDetail();
        productDetail.setCalorie(500.0);
        productDetail.setCarbohydrate(20.0);
        productDetail.setSugars(10.0);
        productDetail.setProtein(30.0);
        productDetail.setFat(15.0);
        productDetail.setAturatedFat(5.0);
        productDetail.setNatrium(5.0);

        ProductsInfoRequest request = new ProductsInfoRequest("악",1000L,"악악", productDetail);


        System.out.println("file :  " + fileList);
        System.out.println("request :  " + request);

        productsService.register(fileList, request);
    }

}
