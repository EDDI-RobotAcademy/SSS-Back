package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "category_uq_category_name",
                columnNames = {"categoryType"})
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    /**
     * categoryName : 재료의 분류 (ex. 채소, 육류...)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType categoryType;

    public Category (CategoryType categoryType) {
        this.categoryType = categoryType;
    }


    public CategoryType getCategoryType() {
        return categoryType;
    }

    public Long getCategoryId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryType=" + categoryType +
                '}';
    }
}

