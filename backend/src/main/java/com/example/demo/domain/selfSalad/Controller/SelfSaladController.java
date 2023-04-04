package com.example.demo.domain.selfSalad.Controller;

import com.example.demo.domain.selfSalad.Controller.request.IngredientImgModifyForm;
import com.example.demo.domain.selfSalad.Controller.response.IngredientInfoReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.Controller.request.IngredientRegisterForm;
import com.example.demo.domain.selfSalad.service.SelfSaladService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/selfsalad")
public class SelfSaladController {
    final private SelfSaladService selfSaladService;

    public SelfSaladController(SelfSaladService selfSaladService) {
        this.selfSaladService = selfSaladService;
    }

    @GetMapping("/list")
    public List<IngredientListResponse> ingredientList() {
        String defaultIngredientType = "VEGETABLE";
        log.info("ingredientList()");

        return selfSaladService.list(defaultIngredientType);
    }
    @GetMapping("/list/{category}")
    public List<IngredientListResponse> ingredientList(@PathVariable("category") String categoryName) {
        log.info("ingredientList()");
        log.info("전달 받은 카테고리 이름 : "+categoryName);

        return selfSaladService.list(categoryName);
    }


    /**
     * VueToSpring
     * 이미지 경로,
     * List { 식별자, 재료명, 재료 분류, List (최대-최수-단위-측정단위 ) }
     * @param imageFile
     */
    @PostMapping(value = "/register",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void ingredientRegister(
            @RequestPart(value = "imageFile")MultipartFile imageFile,
            @RequestPart(value = "ingredientInfo") IngredientRegisterForm ingredientRegisterForm) throws IOException {
        log.info("ingredientRegister(): " + ingredientRegisterForm);

        selfSaladService.register( ingredientRegisterForm.toIngredientRegisterRequest(imageFile));

    }

    /**
     * 재료 INFO 수정 전, INFO 정보 요청
     * @param ingredientId
     * @return ingredient id, name, beforeEditedImg, categoryType,
     */
    @GetMapping("/read/info/{ingredientId}")
    public IngredientInfoReadResponse ingredientInfoRead (@PathVariable("ingredientId") Long ingredientId) {
        log.info("Image Modify");

        return selfSaladService.findIngredientInfo( ingredientId );
    }
    /**
     * 이미지 수정된 정보 등록
     */
    @PutMapping(value = "/modify/img/{ingredientId}",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public void ingredientImgModify (
            @RequestPart(value = "imageFile")MultipartFile imageFile,
            @PathVariable("ingredientId") Long ingredientId) throws IOException {
        log.info("ingredient-img-modify(): ");
        IngredientImgModifyForm ingredientImgModifyForm = new IngredientImgModifyForm();

        selfSaladService.modifyIngredientImg( ingredientId,
                                              ingredientImgModifyForm.modifyEditedImg( imageFile) );

    }



}
