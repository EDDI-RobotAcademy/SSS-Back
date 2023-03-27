package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.request.IngredientRegisterRequest;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SelfSaladService {

    void createCategory(Long id, String name);
    List<Ingredient> list(String ingredientType);


    boolean register(MultipartFile imageFile, IngredientRegisterRequest ingredientRegisterRequest);
}
