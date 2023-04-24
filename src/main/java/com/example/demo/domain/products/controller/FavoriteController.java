package com.example.demo.domain.products.controller;

import com.example.demo.domain.products.controller.form.FavoriteResponse;
import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.service.FavoriteService;
import com.example.demo.domain.utility.TokenBasedController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products/favorite")
@RequiredArgsConstructor
public class FavoriteController extends TokenBasedController {

    final private FavoriteService favoriteService;

    @PostMapping("/changeLike/{productId}")
    public FavoriteResponse changeLike(@PathVariable("productId") Long productId,
                                       HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        log.info("changeLike(): " + productId);
        return favoriteService.changeLike(memberId, productId);
    }

    @PostMapping("/likeStatus/{productId}")
    public Boolean likeStatus(@PathVariable("productId") Long productId,
                              HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        log.info("likeStatus(): " + productId);
        return favoriteService.likeStatus(memberId, productId);
    }

    @GetMapping("/myFavorite")
    public List<Favorite> favoriteList(HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        log.info("favoriteList()-memberId: " + memberId);
        return favoriteService.favoriteList(memberId);
    }
}