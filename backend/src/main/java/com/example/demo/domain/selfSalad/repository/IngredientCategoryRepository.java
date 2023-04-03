package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
}
