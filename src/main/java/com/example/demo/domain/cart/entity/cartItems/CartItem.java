package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Item_Category")
@Entity
public abstract class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    Cart cart;

    @CreationTimestamp
    private Date addedDate;

    public CartItem(Integer quantity, Cart cart) {
        this.quantity = quantity;
        this.cart = cart;
    }

    public Long getId(){
        return this.id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public void modifyQuantity(int quantity) {
        this.setQuantity(this.getQuantity() + quantity);
    }


    public String getCartItemType() {
        DiscriminatorValue discriminatorValue = this.getClass().getAnnotation(DiscriminatorValue.class);
        return discriminatorValue != null ? discriminatorValue.value() : null;
    }

}
