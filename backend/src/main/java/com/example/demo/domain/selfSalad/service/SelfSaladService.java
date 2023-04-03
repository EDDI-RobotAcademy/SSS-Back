package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.response.IngredientImgReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;

import java.io.FileNotFoundException;
import java.util.List;


public interface SelfSaladService {
    boolean register(IngredientRegisterRequest ingredientRegisterRequest);

    List<IngredientListResponse> list(String requestType);

    IngredientImgReadResponse findIngredientImg(Long ingredientId);

    void modifyIngredientImg(Long ingredientId, String modifyImg ) throws FileNotFoundException;
}
