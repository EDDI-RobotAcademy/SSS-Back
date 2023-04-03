package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.IngredientAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Long> {
}
