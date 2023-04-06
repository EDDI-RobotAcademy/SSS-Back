package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.Category;
import com.example.demo.domain.selfSalad.entity.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryType(CategoryType categoryType);
}
