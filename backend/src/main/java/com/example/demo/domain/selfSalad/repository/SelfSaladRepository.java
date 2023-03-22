package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfSaladRepository extends JpaRepository<Ingredient, Long> {
}
