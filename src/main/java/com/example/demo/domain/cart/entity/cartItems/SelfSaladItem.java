package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SELF")
@NoArgsConstructor
public class SelfSaladItem extends CartItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "self_salad_id", nullable = true)
    private SelfSalad selfSalad;

    public SelfSaladItem(Integer quantity, SelfSalad selfSalad, Cart cart) {
        super(quantity, cart);
        this.quantity = quantity;
        this.selfSalad = selfSalad;
        this.cart = cart;
    }
    @Override
    public Long getId() {
        return this.id;
    }

    public SelfSalad getSelfSalad(){
        return this.selfSalad;
    }

    @Override
    public String getCartItemType() {
        return "SELF";
    }
}
