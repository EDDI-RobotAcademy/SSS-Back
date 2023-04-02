package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.IngredientImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientImgRepository extends JpaRepository<IngredientImg, Long> {

}
