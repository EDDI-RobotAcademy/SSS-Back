package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.FavoriteResponse;
import com.example.demo.domain.products.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse changeLike(Long memberId, Long productId);

    Boolean likeStatus(Long memberId, Long productId);

    List<Favorite> favoriteList(Long memberId);
}