package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.request.IngredientInfoRequest;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SelfSaladService {

    List<Ingredient> list();

    void register(MultipartFile imageFile, IngredientInfoRequest ingredientInfoRequest);
}
