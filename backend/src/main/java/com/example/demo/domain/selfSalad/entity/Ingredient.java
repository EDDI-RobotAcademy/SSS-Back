package com.example.demo.domain.selfSalad.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // 재료명
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="category_id")
    @JsonIgnore
    private Category category;

    @OneToOne(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private IngredientImage ingredientImage;

    @OneToOne(mappedBy = "ingredient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Amount amount;


    public Ingredient(String name, Amount amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(String name, Amount amount, IngredientImage image) {
        this.name = name; this.amount = amount; this.ingredientImage = image;
    }


    /**
     * 해당 category 에 새로운 재료를 등록
     * @param category
     */
    public void registerToCategory(Category category) {
        this.category = category;
        this.category.registerIngredient(this);
    }

    /**
     * 재료 이미지
     * @param ingredientImage
     */
    public void registerImage(IngredientImage ingredientImage){
        this.ingredientImage = ingredientImage;
    }

    public void registerAmount( Amount amount){this.amount = amount;}


    public void registerName(String name) {
        this.name = name;
    }
}
