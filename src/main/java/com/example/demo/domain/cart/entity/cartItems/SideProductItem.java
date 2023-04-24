package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("SIDE") // 하위클래스
public class SideProductItem extends CartItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "side_product_id", nullable = true)
    private SideProduct sideProduct;

    public SideProductItem(int quantity, SideProduct sideProduct, Cart cart) {
        super(quantity, cart);
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.cart = cart;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public SideProduct getSideProduct(){
        return this.sideProduct;
    }

    @Override
    public String getCartItemType() {
        return "SIDE";
    }

}
