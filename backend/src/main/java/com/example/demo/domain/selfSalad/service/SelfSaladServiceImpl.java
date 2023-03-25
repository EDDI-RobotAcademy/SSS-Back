package com.example.demo.domain.selfSalad.service;

import com.example.demo.domain.selfSalad.Controller.request.IngredientInfoRequest;
import com.example.demo.domain.selfSalad.entity.*;
//import com.example.demo.domain.selfSalad.entity.convert.IngredientTypeConverter;
//import com.example.demo.domain.selfSalad.entity.convert.MeasureTypeConverter;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class SelfSaladServiceImpl implements SelfSaladService {

    @Autowired
    final private SelfSaladRepository selfSaladRepository;

    public SelfSaladServiceImpl(SelfSaladRepository selfSaladRepository) {
        this.selfSaladRepository = selfSaladRepository;
    }

    @Override
    public List<Ingredient> list(){
         return selfSaladRepository.findAll(Sort.by(Sort.Direction.DESC, "ingredientId") );

    }

    @Override
    public void register(MultipartFile imageFile, IngredientInfoRequest ingredientInfoRequest){
        // request 객체에 실린 데이터들을 entity 로 담기 > repository 에 저장

        // 1. 이름
        Ingredient ingredient = new Ingredient();
        ingredient.setName( ingredientInfoRequest.getName());

        // 2. 이미지
        final String fixedStringPath = "../../SSS-Front/frontend/src/assets/selfSaladImgs/";
        try {
            log.info("requestImageFile - filename: " + imageFile.getOriginalFilename());
            String fullPath = fixedStringPath + imageFile.getOriginalFilename();

            FileOutputStream writer = new FileOutputStream(
                    fixedStringPath + imageFile.getOriginalFilename()
            );
            writer.write(imageFile.getBytes());
            writer.close();

            ImageResource imageResource = new ImageResource(imageFile.getOriginalFilename());
            ingredient.setImageResource(imageResource);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 3. 재료 분류 : IngredientType
        log.info("requestIngredientType : " + ingredientInfoRequest.getCategory());
        String selectedType = ingredientInfoRequest.getCategory();

        Category category = new Category( IngredientType.valueOfTypeName(selectedType));
        ingredient.setCategory(category);


        // 4-1. 측정단위 : MeasureType
        log.info("requestMeasureType : " + ingredientInfoRequest.getMeasure());
        String selectedMeasure = ingredientInfoRequest.getMeasure();

        Amount measure = new Amount( MeasureType.valueOfMeasureName(selectedMeasure));

        // 4-2. 최대, 최소, 단위, 측정 단위 : Amount
        Amount amount = new Amount( ingredientInfoRequest.getMax(), ingredientInfoRequest.getMin(),
                    ingredientInfoRequest.getUnit(), measure.getMeasureType());

        ingredient.setAmount(amount);

        selfSaladRepository.save(ingredient);
    }

}
