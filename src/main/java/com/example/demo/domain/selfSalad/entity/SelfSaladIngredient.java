package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class SelfSaladIngredient {
    /**
     * SelfSalad_Ingredient : 소비자가 선택한 재료의 대한 정보
     * 양파 - 30 - g
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_id")
    private SelfSalad selfSalad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column
    private Long selectedAmount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "amount_id")
    private Amount amount;


    public SelfSaladIngredient(SelfSalad selfSalad, Ingredient ingredient, Long selectedAmount, Amount amount) {
        this.selfSalad = selfSalad;
        this.ingredient = ingredient;
        this.selectedAmount = selectedAmount;
        this.amount = amount;
    }

    public void setSelectedAmount(Long selectedAmount) {
        this.selectedAmount = selectedAmount;
    }
}
