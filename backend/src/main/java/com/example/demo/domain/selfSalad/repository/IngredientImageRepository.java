package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.IngredientImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientImageRepository extends JpaRepository<IngredientImg, Long> {
//    Optional<IngredientImage> findByIngredient_Id(Long id);
    Optional<IngredientImg> findByIngredientId(Long ingredientId);

}
