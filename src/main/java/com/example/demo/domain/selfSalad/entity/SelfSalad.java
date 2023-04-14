package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class SelfSalad {
    /**
     * ingredientQuantities : 선택한 재료 id 에 해당하는 수량 (30g)
     * 중복값 허용X, 순서 중요X = Set
     */
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String title;

    @OneToMany(mappedBy = "selfSalad", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<SelfSaladIngredient> selfSaladIngredients = new HashSet<>();

    @Column
    private Long totalPrice;

    @Column
    private Long totalCalorie;

    
    public SelfSalad(String title, Long totalPrice, Long totalCalorie) {
        this.title = title;
        this.totalPrice = totalPrice;
        this.totalCalorie = totalCalorie;
    }
}
