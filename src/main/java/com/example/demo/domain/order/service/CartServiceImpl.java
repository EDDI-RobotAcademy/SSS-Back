package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.CartRegisterRequest;
import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.order.entity.items.ItemCategoryType;
import com.example.demo.domain.order.entity.items.ProductItem;
import com.example.demo.domain.order.repository.ProductCartRepository;
import com.example.demo.domain.order.repository.ProductItemRepository;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    final private ProductCartRepository productCartRepository;

    final private ProductItemRepository productItemRepository;

    final private ProductsRepository productsRepository;

    final private MemberRepository memberRepository;


    private Member checkMember(Long memberId){
        Optional<Member> maybeMember =
                memberRepository.findByMemberId(memberId);

        Member member = null;
        if(maybeMember.isPresent()) {
            return member = maybeMember.get();
        }

        log.info("존재하지 않은 회원입니다.");
        return null;
    }
    private Product checkProduct(Long productId ){
        Optional<Product> maybeProduct =
                productsRepository.findById(productId);
        if(maybeProduct.isPresent()){
            log.info("Product "+maybeProduct.get().getProductId()+" 번의 상품이 존재합니다.");

            return maybeProduct.get();
        }

        log.info("해당 Product 상품이 없습니다.");
        return null;
    }

    public void classifyItemCategory(CartRegisterRequest item){

        Member member = requireNonNull(checkMember(item.getMemberId()));
        Product requestProduct = requireNonNull(checkProduct(item.getItemId()));

        Optional<ProductCart> memberProductCart =
                productCartRepository.findByMember_memberId(member.getMemberId());

        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            if(memberProductCart.isEmpty()){
                CreateProductCart(member, item, requestProduct);
            }
            log.info(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
            AddProductItem(memberProductCart.get(), item, requestProduct);
        }
    }

    private void CreateProductCart(Member member, CartRegisterRequest item, Product requestProduct ){

        ProductCart firstCart = ProductCart.builder().member(member).build();

        productCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 product 카트를 생성하였습니다.");

        ProductItem newProductItem = item.toProductItem(requestProduct, firstCart);
        productItemRepository.save(newProductItem);

        log.info(member.getNickname()+" 님의 product 카트에 첫 상품을 추가하였습니다.");
    }

    private void AddProductItem(ProductCart memberCart, CartRegisterRequest item, Product requestProduct){

        Optional<ProductItem> maybeProductItem =
                productItemRepository.findByProduct_productIdAndProductCart_Id(memberCart.getId(), item.getItemId());

        if (maybeProductItem.isPresent()) {
            ProductItem productItem = maybeProductItem.get();

            productItem.setQuantity(productItem.getQuantity() + item.getQuantity());
            productItemRepository.save(productItem);
            log.info(requestProduct.getTitle() + " 상품의 수량을 카트에 추가하였습니다.");

        } else {
            ProductItem newProductItem = item.toProductItem(requestProduct, memberCart);

            productItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
        }
    }


}
