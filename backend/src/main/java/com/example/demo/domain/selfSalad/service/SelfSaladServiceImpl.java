package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.response.IngredientImgReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.*;

import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        // 전달 받은 카테고리가 있는지 확인
    @Override
    @Transactional
    public List<IngredientListResponse> list(String requestType){
        Optional<Category> maybeId =  categoryRepository.findByCategoryType( CategoryType.valueOf(requestType));
        log.info("카테고리 아이디 확인 : "+ maybeId.get()); //Category{id=1, categoryType=VEGETABLE}

        if (maybeId.isEmpty()) {
            log.info("없음!");
            return null;
        }

        Category category = maybeId.get();
        log.info(category.getCategoryId()+"이것이 바로 카테고리 아이디");

        List<Ingredient> ingredientList = ingredientRepository.findByCategoryId(category.getCategoryId());
        List<IngredientListResponse> listResponse =  new ArrayList<>();

        for(Ingredient ingredient : ingredientList){
            listResponse.add(ingredient.toResponseList( ingredient));
        }

        return  listResponse;
    }

    @Override
    public IngredientImgReadResponse findIngredientImg (Long ingredientId ){
        Optional<Ingredient> maybeIngredientId = ingredientRepository.findById( ingredientId);

        if (maybeIngredientId.isEmpty()) {
            log.info("없음!");
            return null;
        }
        Ingredient ingredient = maybeIngredientId.get();

        IngredientImgReadResponse imgResponse = ingredient.toImgResponse( ingredient);

        return imgResponse;
    }


}
