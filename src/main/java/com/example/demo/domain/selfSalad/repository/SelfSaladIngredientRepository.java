package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.SelfSaladIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfSaladIngredientRepository  extends JpaRepository<SelfSaladIngredient, Long> {
}
