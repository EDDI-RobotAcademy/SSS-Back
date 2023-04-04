package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.response.IngredientInfoReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.*;

import com.example.demo.domain.selfSalad.service.request.IngredientInfoModifyRequest;
import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    final private IngredientImgRepository ingredientImgRepository;

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
    @Transactional
    public IngredientInfoReadResponse findIngredientInfo(Long ingredientId) {
        Optional<Ingredient> maybeIngredientId = ingredientRepository.findById(ingredientId);

        if (maybeIngredientId.isEmpty()) {
            log.info("없음!");
            return null;
        }
        Ingredient ingredient = maybeIngredientId.get();

        IngredientInfoReadResponse infoResponse = ingredient.toInfoResponse(ingredient);

        return infoResponse;
    }


    private void deleteImgFile(Long ingredientId)throws FileNotFoundException {

        final IngredientImg ingredientImg
                = ingredientImgRepository.findByIngredientId(ingredientId);

        final String fixedStringPath = "../../SSS-Front/frontend/src/assets/selfSalad/";
        Path filePath = Paths.get(fixedStringPath, ingredientImg.getEditedImg());
        //File 객체로 변환
        File file = filePath.toFile();
        if (file.exists()) {
            file.delete();
        } else {
            throw new FileNotFoundException("이미지 파일이 존재하지 않습니다.");
        }
    }

    private void modifyIngredientCategory( Long ingredientId, CategoryType categoryType) {

        final Category category =
                categoryRepository.findByCategoryType(categoryType).get();

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId( ingredientId);

        ingredientCategory.setCategory(category);

        ingredientCategoryRepository.save(ingredientCategory);
    }

    private void modifyIngredient( Long ingredientId, String ingredientName, String modifyImg ){

        final Ingredient ingredient =
                ingredientRepository.findById(ingredientId).get();

        ingredient.setName(ingredientName);
        ingredientRepository.save(ingredient);

        final IngredientImg ingredientImg =
                ingredientImgRepository.findByIngredientId(ingredientId);

        ingredientImg.setEditedImg(modifyImg);
        ingredientImgRepository.save(ingredientImg);

    }

    @Override
    public void modifyIngredientInfo(Long ingredientId,
                                     IngredientInfoModifyRequest modifyRequest) throws FileNotFoundException {

        // 수정 전 이미지 파일을 폴더에서 삭제
        deleteImgFile(ingredientId);
        log.info("이미지 삭제 성공");

        // 이름 및 이미지 수정
        modifyIngredient(ingredientId, modifyRequest.getName(), modifyRequest.getModifyEditedImg());
        log.info("수정 이미지명: "+modifyRequest.getModifyEditedImg());
        log.info("이름 및 이미지 수정 성공");

        // 카테고리 수정
        modifyIngredientCategory(ingredientId, modifyRequest.getCategoryType());
        log.info("카테고리 수정 성공");

    }

}
