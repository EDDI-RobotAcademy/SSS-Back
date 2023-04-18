package com.example.demo.domain.selfSalad.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SelfSalad {
    /**
     * ingredientQuantities : 선택한 재료 id 에 해당하는 수량 (30g)
     * 중복값 허용X, 순서 중요X = Set
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    public void setTitle(String modifyTitle){
        this.title = modifyTitle;
    }
    public void setTotal(Long totalPrice, Long totalCalorie){
        this.totalPrice = totalPrice;
        this.totalCalorie = totalCalorie;
    }
}
