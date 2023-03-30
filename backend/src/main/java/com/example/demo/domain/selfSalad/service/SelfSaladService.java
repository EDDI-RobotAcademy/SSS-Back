package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;


public interface SelfSaladService {
    boolean register(IngredientRegisterRequest ingredientRegisterRequest);
}
