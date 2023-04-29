//package com.example.demo.product.favorite;
//
//import com.example.demo.domain.products.service.FavoriteService;
//import com.example.demo.domain.products.service.request.FavoriteInfoRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class ProductFavoriteTest {
//
//    @Autowired
//    FavoriteService favoriteService;
////    @Autowired
////    LogInTokenForm form;
//
//    @Test
//    void 찜_저장() {
//        FavoriteInfoRequest request = new FavoriteInfoRequest(2L, 1L);
//        System.out.println(favoriteService.changeLike(request));
//    }
//
//    @Test
//    void 찜_상태() {
//        FavoriteInfoRequest request = new FavoriteInfoRequest(1L, 2L);
//        System.out.println(favoriteService.likeStatus(request));
//    }
//
//    @Test
//    void 찜_목록() {
//        System.out.println("목록: " + favoriteService.favoriteList(2L));
//    }
//}
