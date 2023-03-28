package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Amount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column // 재료 최소 단위당 가격
    private Integer price;

    @Column // 재료 최소 단위당 칼로리
    private Integer calorie;

    @Column
    private Integer unit;

    @Column
    private Integer max;

    @Column
    private Integer min;


//    @Enumerated(EnumType.STRING)
 //   @Column(insertable = false, updatable = false, nullable = false)
    @Column(nullable = false)
    private String amountType;
    //private AmountType amountType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;


    //public void to

    public Amount(Integer max, Integer min, Integer unit, String amountType, Ingredient ingredient) {
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.amountType = amountType;
        this.ingredient = ingredient;
    }

    public Amount(Integer max, Integer min, Integer unit, Integer price, Integer calorie, String amountType, Ingredient ingredient) {
        this.max = max;
        this.min = min;
        this.unit = unit;
        this.price = price;
        this.calorie = calorie;
        this.amountType = amountType;
        this.ingredient = ingredient;
    }

    public void registerToIngredient(){this.ingredient.registerAmount(this); }


}
