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
                        request.getCalorie(),
                        request.getUnit(),
                        request.getMax());

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
                        ingredient.getPrice(),
                        ingredient.getIngredientImg().getEditedImg(),
                        responseAmount.getAmount().getAmountType().toString(),
                        responseAmount.getMax(),
                        responseAmount.getUnit(),
                        responseAmount.getCalorie()
                ));
            }
        }
        return  listResponse;
    }

    @Override
    @Transactional
    public IngredientInfoReadResponse findIngredientInfo(Long ingredientId) {
        final Optional<Ingredient> maybeIngredient = ingredientRepository.findById(1L);

        if(maybeIngredient.isEmpty()){
            log.info("일치하는 재료가 없습니다.");
        }
        Ingredient ingredient = maybeIngredient.get();

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

        final Category category =
                categoryRepository.findByCategoryType(categoryType).get();

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId( ingredientId);

        ingredientCategory.setCategory(ingredient, category);

        ingredientCategoryRepository.save(ingredientCategory);
    }

    private void modifyIngredientImg( Ingredient ingredient, Long ingredientId, String modifyImg ) throws FileNotFoundException {

        final IngredientImg ingredientImg =
                ingredientImgRepository.findByIngredientId(ingredientId);

        // 수정 전 이미지 폴더에서 삭제
        deleteImgFile(ingredientImg);

        ingredientImg.setEditedImg(ingredient, modifyImg);

        ingredientImgRepository.save(ingredientImg);
    }

    @Override
    public void modifyIngredientInfo(Long ingredientId,
                                     IngredientInfoModifyRequest modifyRequest) throws FileNotFoundException {

        Optional<Ingredient> maybeIngredient =
                ingredientRepository.findById(ingredientId);

        if(maybeIngredient.isEmpty()){
            log.info("선택한 재료가 없습니다.");
        }
        Ingredient ingredient = maybeIngredient.get();

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

        Optional<Ingredient> maybeIngredient =
                ingredientRepository.findById(ingredientId);
        if(maybeIngredient.isEmpty()){
            log.info("선택한 재료가 없습니다.");
        }
        Ingredient ingredient = maybeIngredient.get();

        final IngredientAmount ingredientAmount =
                ingredientAmountRepository.findByIngredientId(ingredientId);

        IngredientAmountReadResponse amountResponse =
                new IngredientAmountReadResponse(ingredient.getName(),
                                                 ingredient.getPrice(),
                                                 ingredientAmount.getCalorie(),
                                                 ingredientAmount.getUnit(),
                                                 ingredientAmount.getMax(),
                                                 ingredientAmount.getAmount().getAmountType().toString()
        );
        return amountResponse;
    }

    public void modifyIngredientAmount(Long ingredientId, IngredientAmountModifyRequest modifyRequest){

        Optional<Ingredient> maybeIngredient =
                ingredientRepository.findById(ingredientId);
        if(maybeIngredient.isEmpty()){
            log.info("선택한 재료가 없습니다.");
        }
        Ingredient ingredient = maybeIngredient.get();

        ingredient.setPrice(modifyRequest.getPrice());

        final Amount amount =
                amountRepository.findByAmountType(modifyRequest.getAmountType()).get();

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
