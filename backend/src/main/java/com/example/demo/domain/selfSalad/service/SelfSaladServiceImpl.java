package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.*;

import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.demo.domain.selfSalad.Controller.request.IngredientRegisterForm;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelfSaladServiceImpl implements SelfSaladService {

    // repository
    final private CategoryRepository categoryRepository;
    final private IngredientRepository ingredientRepository;
    final private AmountRepository amountRepository;
    final private IngredientAmountRepository ingredientAmountRepository;
    final private IngredientCategoryRepository ingredientCategoryRepository;

    /**
     * 사전에 enum (카테고리, 수량) 저장하기
     */
    @PostConstruct
    private void initSetCategory (){
        List<Category> categoryList = new ArrayList<>(Arrays.asList(
                new Category(CategoryType.VEGETABLE),
                new Category(CategoryType.MEAT),
                new Category(CategoryType.TOPPING)
        ));
        categoryRepository.saveAll(categoryList);
    }
    @PostConstruct
    private void initSetAmount (){
        List<Amount> amountList = new ArrayList<>(Arrays.asList(
                new Amount(AmountType.GRAM),
                new Amount(AmountType.COUNT)
        ));
        amountRepository.saveAll(amountList);
    }
    /**
     * 재료 등록 절차 1 : Ingredient > IngredientRepository
     * @param request
     * @return
     */
    private Ingredient registerIngredient (IngredientRegisterRequest request) {
        final Ingredient ingredient = request.toIngredient();
        ingredientRepository.save(ingredient);

        return ingredient;
    }

    /**
     * 재료 등록 절차 2 : Amount > AmountRepository
     * @param ingredient
     * @param request
     */
    private void registerIngredientAmount (
            Ingredient ingredient, IngredientRegisterRequest request) {
        log.info("잘 받아왔니??"+request.getAmountType().toString());

        final Amount amount =
                amountRepository.findByAmountType(request.getAmountType()).get();

        final IngredientAmount ingredientAmount =
                new IngredientAmount(ingredient, amount,
                        request.getPrice(),
                        request.getCalorie(),
                        request.getUnit(),
                        request.getMax(),
                        request.getMin());

        ingredientAmountRepository.save(ingredientAmount);
    }

    /**
     * 재료 등록 절차 3 : Ingredient, Request > Category
     * @param ingredient
     * @param request
     */
    public void registerIngredientCategory(
            Ingredient ingredient, IngredientRegisterRequest request) {
        log.info("카테고리 잘 받아왔니??"+request.getCategoryType().toString());

        final Category category =
                categoryRepository.findByCategoryType(request.getCategoryType()).get();

        final IngredientCategory ingredientCategory =
                new IngredientCategory(ingredient, category);

        ingredientCategoryRepository.save(ingredientCategory);
    }

    /**
     * 재료 등록 절차 4 (최종)
     * @param ingredientRegisterRequest
     * @return
     */
    @Override
    public boolean register(IngredientRegisterRequest ingredientRegisterRequest) {
        // 1. 재료 entity 및 image 생성
        Ingredient ingredient = registerIngredient(ingredientRegisterRequest);

        // 2. 수량 entity 생성
        registerIngredientAmount(ingredient, ingredientRegisterRequest);

        // 3. 카테고리 처리
        registerIngredientCategory(ingredient, ingredientRegisterRequest);

        return true;
    }


}
