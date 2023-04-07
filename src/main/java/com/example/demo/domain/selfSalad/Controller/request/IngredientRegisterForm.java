package com.example.demo.domain.selfSalad.Controller.request;
import com.example.demo.domain.selfSalad.entity.AmountType;
import com.example.demo.domain.selfSalad.entity.CategoryType;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class IngredientRegisterForm {

    final private String name;

    // enum
    @JsonProperty("category")
    final private CategoryType categoryType;

    // IngredientAmount
    final private Integer price;
    final private Integer calorie;
    final private Integer max;
    final private Integer min;
    final private Integer unit;

    // Amount - AmountType(enum)
    @JsonProperty("measure")
    final private AmountType amountType;

    public IngredientRegisterRequest toIngredientRegisterRequest (MultipartFile imageFile) throws IOException {
        UUID randomName = UUID.randomUUID();
        String editedImg = randomName + imageFile.getOriginalFilename();

        final String fixedStringPath = "../SSS-Front/frontend/src/assets/selfSalad/";
        FileOutputStream writer = new FileOutputStream(
                fixedStringPath + editedImg
        );

        writer.write(imageFile.getBytes());
        writer.close();


        return new IngredientRegisterRequest(
                name, categoryType, price, calorie,
                max, min, unit, amountType, editedImg);
    }

}
