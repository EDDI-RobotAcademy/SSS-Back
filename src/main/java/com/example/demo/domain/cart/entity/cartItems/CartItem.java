package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import com.example.demo.domain.sideProducts.entity.SideProduct;
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public String getCartItemType() {
        DiscriminatorValue discriminatorValue = this.getClass().getAnnotation(DiscriminatorValue.class);
        return discriminatorValue != null ? discriminatorValue.value() : null;
    }

    public Product getProduct() {
        return this.getProduct();
    }

    public SideProduct getSideProduct() {
        return this.getSideProduct();
    }
    public SelfSalad getSelfSalad(){
        return this.getSelfSalad();
    }
}
