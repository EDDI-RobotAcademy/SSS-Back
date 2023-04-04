package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.response.IngredientAmountReadResponse;
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

import javax.swing.text.html.Option;
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
        Category category =  categoryRepository.findByCategoryType( CategoryType.valueOf(requestType)).get();
        log.info(category.getCategoryId()+"이것이 바로 카테고리 아이디");

        List<Ingredient> ingredientList = ingredientRepository.findByCategoryId(category.getCategoryId());
        List<IngredientListResponse> listResponse =  new ArrayList<>();

        for(Ingredient ingredient : ingredientList){
            for (IngredientAmount responseAmount : ingredient.getIngredientAmounts()) {
                listResponse.add( new IngredientListResponse(

                        ingredient.getId(),
                        ingredient.getName(),
                        ingredient.getIngredientImg().getEditedImg(),
                        responseAmount.getAmount().getAmountType().toString(),
                        responseAmount.getMax(),
                        responseAmount.getMin(),
                        responseAmount.getUnit(),
                        responseAmount.getPrice(),
                        responseAmount.getCalorie()
                ));
            }
        }
        return  listResponse;
    }

    @Override
    @Transactional
    public IngredientInfoReadResponse findIngredientInfo(Long ingredientId) {
        final Ingredient ingredient = ingredientRepository.findById(ingredientId).get();

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId(ingredientId);

        return new IngredientInfoReadResponse(
                ingredient.getName(),
                ingredient.getIngredientImg().getEditedImg(),
                ingredientCategory.getCategory().getCategoryType().toString()
        );
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

    private void modifyIngredientName( Long ingredientId, String ingredientName ){

        final Ingredient ingredient =
                ingredientRepository.findById(ingredientId).get();

        ingredient.setName(ingredientName);
        ingredientRepository.save(ingredient);

    }
    private void modifyIngredientImg( Long ingredientId, String modifyImg ){

        final IngredientImg ingredientImg =
                ingredientImgRepository.findByIngredientId(ingredientId);

        ingredientImg.setEditedImg(modifyImg);
        ingredientImgRepository.save(ingredientImg);

    }

    @Override
    public void modifyIngredientInfo(Long ingredientId,
                                     IngredientInfoModifyRequest modifyRequest) throws FileNotFoundException {

        // 수정 전 이미지 파일을 폴더에서 삭제
        if( ! modifyRequest.getModifyEditedImg().equals("notImgChange")){
            deleteImgFile(ingredientId);
            log.info("이미지 삭제 성공");

            modifyIngredientImg(ingredientId, modifyRequest.getModifyEditedImg());
            log.info("이미지 수정 성공");

        }
        log.info("이미지 수정 요청 들어왔을까? "+modifyRequest.getModifyEditedImg());
        // 이름 수정
        modifyIngredientName(ingredientId, modifyRequest.getName());
        log.info("수정 이미지명: "+modifyRequest.getModifyEditedImg());
        log.info("이름 수정 성공");

        // 카테고리 수정
        modifyIngredientCategory(ingredientId, modifyRequest.getCategoryType());
        log.info("카테고리 수정 성공");

    }
    @Override
    @Transactional
    public IngredientAmountReadResponse findIngredientAmount(Long ingredientId) {
        final String ingredientName =
                ingredientRepository.findById(ingredientId).get().getName();

        final IngredientAmount ingredientAmount =
                ingredientAmountRepository.findByIngredientId(ingredientId);

        IngredientAmountReadResponse amountResponse =
                new IngredientAmountReadResponse(ingredientName,
                                                 ingredientAmount.getPrice(),
                                                 ingredientAmount.getCalorie(),
                                                 ingredientAmount.getUnit(),
                                                 ingredientAmount.getMax(),
                                                 ingredientAmount.getMin(),
                                                 ingredientAmount.getAmount().getAmountType().toString()
        );
        return amountResponse;
    }

}
