package com.example.demo.domain.selfSalad.repository;


import com.example.demo.domain.selfSalad.entity.Category;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    @Query("select distinct i from Ingredient i where i.category.id = :categoryId")
            //join amount a on i.id = a.ingredient_id join ingredient_image g on i.id = g.id
//select * from ingredient i join amount a on i.id = a.ingredient_id join ingredient_image g on i.id = g.id;
    List<Ingredient> findByCategoryId( Long categoryId );

    /*
        @Query("select n from NovelInformation n join fetch n.member m where m.nickName = :nickName order by n.id desc")
    Slice<NovelInformation> findNovelInformationById(Pageable pageable, String nickName);
     */


//   @Query(value = "select c from Ingredient i join fetch i.category c where c.ingredient_type = :ingredientType", nativeQuery = true)

    // 재료타입에 따른 재료 list 반환
//    @Query(value = "select i from Ingredient i join fetch i.category c where c.ingredient_type = :ingredientType", nativeQuery = true)
//    List<Ingredient> findByCategoryType(CategoryType categoryType);




//    @Query("select c from Category c where c.ingredient_type = :ingredientType")
//    @Query("select i from Ingredient i join fetch i.category c where i.category_id= c.categoryId ")
//    Category findByIngredientType(IngredientType ingredientType);


}
