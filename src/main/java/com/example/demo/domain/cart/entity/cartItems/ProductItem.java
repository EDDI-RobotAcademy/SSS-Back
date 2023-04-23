package com.example.demo.domain.cart.entity.cartItems;

import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.products.entity.Product;

import javax.persistence.*;

@Entity
@DiscriminatorValue("PRODUCT") // 하위클래스 // 부모와 맵핑될 때 itemCategoryType 컬럼 값으로 PRODUCT를 저장
public class ProductItem extends CartItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public ProductItem(Integer quantity, Product product, Cart cart) {
        super(quantity, cart);
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }
    @Override
    public Product getProduct(){
        return this.product;
    }

    @Override
    public String getCartItemType() {
        return "PRODUCT";
    }
}
