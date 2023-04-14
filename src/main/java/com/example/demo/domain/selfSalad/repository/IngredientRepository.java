package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query(value = "select * from ingredient i join ingredient_category ic where i.id = ic.ingredient_id and ic.category_Id = :categoryId", nativeQuery = true)
    List<Ingredient> findByCategoryId(Long categoryId);

    Optional<List<Ingredient>> findByIdIn(List<Long> ingredientIds);
}
