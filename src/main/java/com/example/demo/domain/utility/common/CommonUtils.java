package com.example.demo.domain.utility.common;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.repository.IngredientRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;

public class CommonUtils {

    public static Member getMemberById(MemberRepository memberRepository, Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다: " + memberId));
    }

    public static Product getProductById(ProductsRepository productsRepository, Long productId) {
     return productsRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("등록된 Product 상품이 아닙니다. : " + productId));

    }

    public static SideProduct getSideProductById(SideProductsRepository sideProductsRepository, Long sideProductId) {
        return sideProductsRepository.findById(sideProductId)
                .orElseThrow(() -> new RuntimeException("등록된 SideProduct 상품이 아닙니다. : " + sideProductId));
    }

    public static Ingredient getIngredientById(IngredientRepository ingredientRepository, Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new RuntimeException("등록된 Ingredient 가 아닙니다. : " + ingredientId));
    }


}
