package com.example.demo.domain.selfSalad.Controller.request;

import com.example.demo.domain.selfSalad.entity.CategoryType;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.entity.IngredientImg;
import com.example.demo.domain.selfSalad.service.request.IngredientInfoModifyRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Getter
@ToString
@RequiredArgsConstructor
public class IngredientInfoModifyForm {

    final private String name;

    @JsonProperty("categoryType") // vue 에서 받아온 카테고리 데이터
    final private CategoryType categoryType;

    /**
     * 수정요청 이미지 UUID 변경 및 폴더에 저장
     * @param imageFile
     * @return
     * @throws IOException
     */
    public IngredientInfoModifyRequest modifyEditedImg (MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty()) {
            UUID randomName = UUID.randomUUID();
            String modifyEditedImg = randomName + imageFile.getOriginalFilename();

            final String fixedStringPath = "../SSS-Front/src/assets/selfSalad/";
            FileOutputStream writer = new FileOutputStream(
                    fixedStringPath + modifyEditedImg
            );

            writer.write(imageFile.getBytes());
            writer.close();

            return new IngredientInfoModifyRequest(this.name, this.categoryType, modifyEditedImg);
        }else{
            String modifyEditedImg = "notImgChange";
            return new IngredientInfoModifyRequest(this.name, this.categoryType, modifyEditedImg);

        }


    }


}
