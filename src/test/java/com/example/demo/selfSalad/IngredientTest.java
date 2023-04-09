package com.example.demo.selfSalad;

import com.example.demo.domain.selfSalad.Controller.request.IngredientRegisterForm;
import com.example.demo.domain.selfSalad.Controller.response.IngredientInfoReadResponse;
import com.example.demo.domain.selfSalad.Controller.response.IngredientListResponse;
import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.*;
import com.example.demo.domain.selfSalad.service.SelfSaladServiceImpl;
import com.example.demo.domain.selfSalad.service.request.IngredientRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IngredientTest {
    @Mock
    private AmountRepository mockAmountRepository;
    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private IngredientAmountRepository mockIngredientAmountRepository;
    @Mock
    private IngredientCategoryRepository mockIngredientCategoryRepository;
    @Mock
    private IngredientRepository mockIngredientRepository;
    @Mock
    private IngredientImgRepository mockIngredientImgRepository;

    //@Mock
    @InjectMocks
    private SelfSaladServiceImpl mockSelfSaladService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientImgRepository ingredientImgRepository;

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AmountRepository amountRepository;

    @Autowired
    private IngredientAmountRepository ingredientAmountRepository;

    private MockMultipartFile mockImgFile;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("enum Test")
    @Test
    public void enum_변환_테스트 () {
        String receivedData = "VEGETABLE";
        CategoryType categoryType = CategoryType.valueOf(receivedData);

        assertEquals(categoryType, CategoryType.VEGETABLE);
    }


    @Test
    private void prepareFileInputTest () throws IOException {
        final String pathToLoad = Paths.get("../SSS-Front/src/assets/selfSalad/onion.png").toString();
        System.out.println("path: " + pathToLoad);
        File file = new File(pathToLoad);
        FileInputStream input = new FileInputStream(file);

        mockImgFile = new MockMultipartFile(
                "testFile",
                file.getName(),
                MediaType.IMAGE_PNG_VALUE,
                Files.readAllBytes(file.toPath()));
    }

//    @Test
//    public void whenFileUploaded_thenVerifyStatus() throws Exception {
//        MockMultipartFile imageFile
//                = new MockMultipartFile(
//                "imageFile",
//                "hello.txt",
//                MediaType.TEXT_PLAIN_VALUE,
//                "Hello, World!".getBytes()
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String content = objectMapper.writeValueAsString(
//                new IngredientRegisterForm("이런", CategoryType.VEGETABLE,
//                                            10000, 50, 5, 1, AmountType.GRAM));
//
//        mockImgFile = new MockMultipartFile(
//                "ingredientInfo",
//                "jsonData",
//                MediaType.APPLICATION_JSON_VALUE,
//                content.getBytes()
//        );
//
//        MockMvc mockMvc
//                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//        mockMvc.perform(multipart("/selfsalad/register")
//                        .file(imageFile)
//                        .file(mockImgFile))
//                .andExpect(status().isOk());
//    }

    @Test
    public void 재료_등록_테스트 () throws Exception {
        IngredientRegisterForm registerForm =
                new IngredientRegisterForm("젠장양파3",
                        CategoryType.VEGETABLE,
                        10000, 50, 5, 1, AmountType.GRAM);

        // 이미지 파일
        prepareFileInputTest();
        System.out.println(mockImgFile.getOriginalFilename());

        // 등록 요청 : ingredient, ingredient_img
        IngredientRegisterRequest registerRequest =
                registerForm.toIngredientRegisterRequest(mockImgFile);

        Ingredient ingredient = registerRequest.toIngredient();
        System.out.println(ingredient);

        ingredientRepository.save(ingredient);

        // 등록 요청 : ingredient_category
        final Category category =
                categoryRepository.findByCategoryType(registerRequest.getCategoryType()).get();
        final IngredientCategory ingredientCategory =
                new IngredientCategory(ingredient, category);

        ingredientCategoryRepository.save(ingredientCategory);

        System.out.println(category);

        // 등록 요청 : ingredient_amount
        final Amount amount =
                amountRepository.findByAmountType(registerRequest.getAmountType()).get();
        final IngredientAmount ingredientAmount =
                new IngredientAmount(ingredient, amount,
                        registerRequest.getCalorie(),
                        registerRequest.getUnit(),
                        registerRequest.getMax());

        ingredientAmountRepository.save(ingredientAmount);
        System.out.println("재료 등록 성공 : "+ingredient.getId());
    }

    @Test
    @Transactional
    public void 재료_리스트_반환_테스트(){

        Category category =  categoryRepository.findByCategoryType( CategoryType.valueOf("VEGETABLE")).get();
        System.out.println(("카테고리 아이디 : "+ category.getCategoryId()));

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

        System.out.println("등록된 재료 출력"+ listResponse.toArray().toString());
    }

    @Test
    @Transactional
    public void 재료_정보_읽기_테스트(){
        final Optional<Ingredient> ingredient = ingredientRepository.findById(1L);

        if(ingredient.isEmpty()){
            System.out.println("일치하는 재료가 없습니다.");
        }

        final IngredientCategory ingredientCategory =
                ingredientCategoryRepository.findByIngredientId(1L);

        IngredientInfoReadResponse infoReadResponse =
                new IngredientInfoReadResponse(
                        ingredient.get().getName(),
                        ingredient.get().getIngredientImg().getEditedImg(),
                        ingredientCategory.getCategory().getCategoryType().toString()
        );

        System.out.println("재료 이름, 이미지, 카티고리 읽기 : "+ infoReadResponse);

    }



}
