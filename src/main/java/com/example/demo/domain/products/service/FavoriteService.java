package com.example.demo.domain.products.service;

import com.example.demo.domain.products.controller.form.FavoriteResponse;
import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.service.response.FavoriteListResponse;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse changeLike(Long memberId, Long productId);

    Boolean likeStatus(Long memberId, Long productId);

    List<FavoriteListResponse> favoriteList(Long memberId);
}