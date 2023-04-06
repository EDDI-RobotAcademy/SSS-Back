package com.example.demo.domain.selfSalad.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class IngredientAmount {
    /**
     * price : 최소 수량(min) 당 가격
     * calorie: 최소 수량(min) 당 칼로리
     * unit : min+ unit 씩 판매 수량이 늘어남 (ex. unit이 20일때 10g-30g-50g)
     * max : 구매자가 선택할 수 있는 최대 수량
     * min : 구매자가 선택할 수 있는 최소 수량
     * amountType : 측정 단위 (g, 개)
     */
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


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "amount_id")
    private Amount amount;


    public IngredientAmount(Ingredient ingredient, Amount amount,
                            Integer price, Integer calorie,
                            Integer unit, Integer max, Integer min) {

        this.ingredient = ingredient;
        this.amount = amount;

        this.price = price;
        this.calorie = calorie;
        this.unit = unit;
        this.max = max;
        this.min = min;
    }

    public Amount getAmount () {
        return amount;
    }

    public void setIngredientAmount(Amount amount, Integer price, Integer calorie,
                                    Integer unit, Integer max, Integer min){
        this.amount = amount;
        this.price = price;
        this.calorie = calorie;
        this.unit = unit;
        this.max = max;
        this.min = min;

    }
}
