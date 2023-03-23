package com.example.demo.domain.selfSalad.Controller;

import com.example.demo.domain.selfSalad.Controller.request.IngredientInfoRequest;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.service.SelfSaladService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/selfsalad")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class SelfSaladController {
    final private SelfSaladService selfSaladService;

    public SelfSaladController(SelfSaladService selfSaladService) {
        this.selfSaladService = selfSaladService;
    }

    @GetMapping("/list")
    public List<Ingredient> ingredientList () {
        log.info("boardList()");

        return selfSaladService.list();
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
            @RequestPart(value = "ingredientInfo") IngredientInfoRequest ingredientInfoRequest){
        log.info("ingredientRegister()");

        selfSaladService.register( imageFile, ingredientInfoRequest);

    }

}
