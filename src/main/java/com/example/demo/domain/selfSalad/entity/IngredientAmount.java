package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class IngredientAmount {
    /**
     * price : unit 당 가격
     * calorie: unit 당 칼로리
     * unit : unit 씩 수량이 늘어남 (ex. unit = 20일때 0g-20g-40g)
     * max : 구매자가 선택할 수 있는 최대 수량
     * amountType : 측정 단위 (g, 개)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column // 재료 단위당 칼로리
    private Integer calorie;

    @Column
    private Integer unit;

    @Column
    private Integer max;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "amount_id")
    private Amount amount;


    public IngredientAmount(Ingredient ingredient, Amount amount,
                            Integer calorie, Integer unit, Integer max) {

        this.ingredient = ingredient;
        this.amount = amount;
        this.calorie = calorie;
        this.unit = unit;
        this.max = max;
    }

    public Amount getAmount () {
        return amount;
    }

    public void modifyIngredientAmount(Amount amount,
                                       Integer calorie, Integer unit, Integer max){
        this.amount = amount;
        this.calorie = calorie;
        this.unit = unit;
        this.max = max;

    }
}
