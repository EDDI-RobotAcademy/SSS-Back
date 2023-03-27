package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.request.IngredientRegisterRequest;
import com.example.demo.domain.selfSalad.entity.*;

import com.example.demo.domain.selfSalad.repository.AmountRepository;
import com.example.demo.domain.selfSalad.repository.CategoryRepository;
import com.example.demo.domain.selfSalad.repository.IngredientImageRepository;
import com.example.demo.domain.selfSalad.repository.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class SelfSaladServiceImpl implements SelfSaladService {

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    IngredientImageRepository ingredientImageRepository;

    @Autowired
    AmountRepository amountRepository;


    /**
     * 새로운 카테고리 생성
     * @param id
     * @param name
     */
    public void createCategory(Long id, String name){
        Category category = new Category(id, name);
        categoryRepository.save(category);
    }

    /**
     * 새로운 재료 등록
     * @param image
     * @param request
     * @return
     */
    @Override
    @Transactional
    public boolean register(MultipartFile image, IngredientRegisterRequest request){

        // 1. 카테고리 찾기
        log.info("requestIngredientType : " + request.getCategory());

        Optional<Category> maybeCategory = categoryRepository.findByCategoryName(  request.getCategory());

        log.info("1 차 시도");

        if (maybeCategory.isEmpty()) {
            return false;
        }
        Category category = maybeCategory.get();

        log.info("카테고리 찾았다");

        // 2. 재료 entity 생성 : 하위에 계속 추가해 최종적으로 리포지터리에 저장
        Ingredient ingredient = new Ingredient();
        ingredient.registerName( request.getName());

        // 2. 수량 entity
        log.info(request.getUnit().toString());
        Amount amount = new Amount(request.getMax(), request.getMin(), request.getUnit(), request.getPrice(), request.getCalorie(),
                request.getMeasure(), ingredient);
        //Amount amount = request.toEntity();

            // 수량 entity 에 재료 넣기
        ingredient.registerAmount(amount);
        log.info(amount.getUnit().toString());

        amountRepository.save(amount);

        log.info("request.toIngredient(amount): Amount 추가");


        // 4. 이미지
        final String fixedStringPath = "../../SSS-Front/frontend/src/assets/selfSaladImgs/";
        try {
            log.info("requestImageFile - filename: " + image.getOriginalFilename());

            UUID randomName = UUID.randomUUID();
            String fileRandomName = randomName + image.getOriginalFilename();

            // 파일 경로지정
            FileOutputStream writer = new FileOutputStream(
                    fixedStringPath + fileRandomName);

            writer.write(image.getBytes());
            writer.close();

            IngredientImage ingredientImage = new IngredientImage(
                    image.getOriginalFilename(),
                    fileRandomName,
                    ingredient
            );

            // Ingredient.class에 재료 이미지 entity 저장
            ingredientImage.registerToIngredient();
            ingredientImageRepository.save(ingredientImage);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 해당 카테고리에 새로운 재료 entity 를 저장
        ingredient.registerToCategory( category);
        log.info("여기였군");

        ingredientRepository.save( ingredient);

        return true;
    }

    @Override
    public List<Ingredient> list(String requestType){

        Optional<Category> maybeCategory = categoryRepository.findByCategoryName(requestType);

        if (maybeCategory.isEmpty()) {
            return null;
        }
        Category category = maybeCategory.get();

        //List <Ingredient>maybeIngredient = ingredientImageRepository.findByCategoryId( category.getId());

        return ingredientRepository.findByCategoryId( category.getId());

    }

}
