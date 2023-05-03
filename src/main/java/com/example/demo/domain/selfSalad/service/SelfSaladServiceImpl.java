package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.response.IngredientAmountReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientInfoReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.*;
import com.example.demo.domain.selfSalad.service.request.IngredientAmountModifyRequest;
import com.example.demo.domain.selfSalad.service.request.IngredientInfoModifyRequest;
import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    final private IngredientImgRepository ingredientImgRepository;

    public Ingredient getIngredientById( Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("등록된 Ingredient 가 아닙니다. : " + ingredientId));
    }

    public Category getCategoryByType(CategoryType categoryType) {
        return categoryRepository.findByCategoryType( categoryType)
                .orElseThrow(() -> new RuntimeException("재료 카테고리를 찾을 수 없습니다. : " + categoryType));
    }

    public Amount getAmountByType(AmountType amountType){
        return amountRepository.findByAmountType(amountType)
                        .orElseThrow(() -> new RuntimeException("등록된 AmountType 이 아닙니다. : " + amountType));
    }


    private Ingredient registerIngredient (IngredientRegisterRequest request) {
        final Ingredient ingredient = request.toIngredient();
        ingredientRepository.save(ingredient);

        log.info("재료가 등록되었습니다. : "+ingredient.getId());
        return ingredient;
    }

    private void registerIngredientAmount (Ingredient ingredient, IngredientRegisterRequest request) {

        Amount amount = getAmountByType(request.getAmountType());

        final IngredientAmount ingredientAmount =
                new IngredientAmount(ingredient, amount,
                        request.getCalorie(),
                        request.getUnit(),
                        request.getMax());

        ingredientAmountRepository.save(ingredientAmount);
        log.info("재료 카테고리가 등록되었습니다. : "+ingredientAmount.getId());
    }


    public void registerIngredientCategory(Ingredient ingredient, IngredientRegisterRequest request) {
        Category category = getCategoryByType(request.getCategoryType());

        final IngredientCategory ingredientCategory =
                new IngredientCategory(ingredient, category);

        ingredientCategoryRepository.save(ingredientCategory);
        log.info("재료 카테고리가 등록되었습니다. : "+category.getCategoryId());
    }


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
        Category category =  getCategoryByType( CategoryType.valueOf(requestType));

        List<Ingredient> ingredientList = ingredientRepository.findByCategoryId(category.getCategoryId());
        List<IngredientListResponse> listResponse =  new ArrayList<>();

        for(Ingredient ingredient : ingredientList){
            for (IngredientAmount amount : ingredient.getIngredientAmounts()) {
                listResponse.add(
                        new IngredientListResponse(ingredient, amount)
                );
            }
        }
        return  listResponse;
    }

    @Override
    @Transactional
    public IngredientInfoReadResponse findIngredientInfo(Long ingredientId) {
        Ingredient ingredient = getIngredientById(ingredientId);

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId(ingredientId);

        return new IngredientInfoReadResponse(
                ingredient.getName(),
                ingredient.getIngredientImg().getEditedImg(),
                ingredientCategory.getCategory().getCategoryType().toString()
        );
    }


    private void deleteImgFile(IngredientImg ingredientImg) {

        final String fixedStringPath = "../SSS-Front/src/assets/selfSalad/";
        Path filePath = Paths.get(fixedStringPath, ingredientImg.getEditedImg());
        //File 객체로 변환
        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    private void modifyIngredientCategory( Ingredient ingredient, Long ingredientId, CategoryType categoryType) {

        final Category category = getCategoryByType(categoryType);

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId( ingredientId);

        ingredientCategory.setCategory(ingredient, category);

        ingredientCategoryRepository.save(ingredientCategory);
    }

    private void modifyIngredientImg( Ingredient ingredient, Long ingredientId, String modifyImg ){

        final IngredientImg ingredientImg =
                ingredientImgRepository.findByIngredientId(ingredientId);

        // 수정 전 이미지 폴더에서 삭제
        deleteImgFile(ingredientImg);

        ingredientImg.setEditedImg(ingredient, modifyImg);

        ingredientImgRepository.save(ingredientImg);
    }

    @Override
    public void modifyIngredientInfo(Long ingredientId,
                                     IngredientInfoModifyRequest modifyRequest){

        Ingredient ingredient = getIngredientById(ingredientId);

        // 재료 이름 수정
        ingredient.setName(modifyRequest.getName());

        // 이미지 수정 요청이 들어온 경우
        if( ! modifyRequest.getModifyEditedImg().equals("notImgChange")) {
            modifyIngredientImg( ingredient, ingredientId,
                                 modifyRequest.getModifyEditedImg());
            log.info("이미지 수정 성공");
        }

        // 카테고리 수정
        CategoryType categoryType = modifyRequest.getCategoryType();
        log.info("수정요청 들어온 카테고리 : "+categoryType.toString());

        modifyIngredientCategory(ingredient, ingredientId, categoryType);
        log.info("카테고리 수정 성공");

        ingredientRepository.save(ingredient);
    }
    @Override
    @Transactional
    public IngredientAmountReadResponse findIngredientAmount(Long ingredientId) {

        Ingredient ingredient = getIngredientById(ingredientId);

        final IngredientAmount ingredientAmount =
                ingredientAmountRepository.findByIngredientId(ingredientId);

        return  new IngredientAmountReadResponse(ingredient, ingredientAmount);
    }

    public void modifyIngredientAmount(Long ingredientId, IngredientAmountModifyRequest modifyRequest){

        Ingredient ingredient = getIngredientById(ingredientId);

        ingredient.setPrice(modifyRequest.getPrice());

        Amount amount = getAmountByType(modifyRequest.getAmountType());

        final IngredientAmount ingredientAmount =
                ingredientAmountRepository.findByIngredientId(ingredientId);

        ingredientAmount.modifyIngredientAmount(
                ingredient,
                amount,
                modifyRequest.getCalorie(),
                modifyRequest.getUnit(),
                modifyRequest.getMax()
        );
        ingredientAmountRepository.save(ingredientAmount);
        ingredientRepository.save(ingredient);
    }

    public void delete(Long ingredientId) {

        final IngredientImg ingredientImg
                = ingredientImgRepository.findByIngredientId(ingredientId);
        deleteImgFile(ingredientImg);

        ingredientRepository.deleteById(ingredientId);
    }

}
