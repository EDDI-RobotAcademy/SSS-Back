package com.example.demo.domain.products.service;

import com.example.demo.domain.products.entity.Favorite;
import com.example.demo.domain.products.service.request.FavoriteInfoRequest;

import java.util.List;

public interface FavoriteService {
    Boolean changeLike(FavoriteInfoRequest request);

    Boolean likeStatus(FavoriteInfoRequest request);

    List<Favorite> favoriteList(Long memberId);
}
