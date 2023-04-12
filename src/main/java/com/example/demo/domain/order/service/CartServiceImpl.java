package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.CartRegisterRequest;
import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.order.entity.SideProductCart;
import com.example.demo.domain.order.entity.items.ItemCategoryType;
import com.example.demo.domain.order.entity.items.ProductItem;
import com.example.demo.domain.order.entity.items.SideProductItem;
import com.example.demo.domain.order.repository.ProductCartRepository;
import com.example.demo.domain.order.repository.ProductItemRepository;
import com.example.demo.domain.order.repository.SideProductCartRepository;
import com.example.demo.domain.order.repository.SideProductItemRepository;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    final private ProductCartRepository productCartRepository;
    final private SideProductCartRepository sideProductCartRepository;

    final private ProductItemRepository productItemRepository;
    final private SideProductItemRepository sideProductItemRepository;

    final private ProductsRepository productsRepository;
    final private SideProductsRepository sideProductsRepository;

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
    private SideProduct checkSideProduct(Long sideProductId ){
        Optional<SideProduct> maybeSideProduct =
                sideProductsRepository.findById(sideProductId);
        if(maybeSideProduct.isPresent()){
            log.info("SideProduct "+maybeSideProduct.get().getSideProductId()+" 번의 상품이 존재합니다.");

            return maybeSideProduct.get();
        }

        log.info("해당 SideProduct 상품이 없습니다.");
        return null;
    }

    public void classifyItemCategory(CartRegisterRequest item){

        Member member = requireNonNull(checkMember(item.getMemberId()));
        Product requestProduct = requireNonNull(checkProduct(item.getItemId()));
        SideProduct requestSideProduct = requireNonNull(checkSideProduct(item.getItemId()));

        Optional<ProductCart> memberProductCart =
                productCartRepository.findByMember_memberId(member.getMemberId());
        Optional<SideProductCart> memberSideProductCart =
                sideProductCartRepository.findByMember_memberId(member.getMemberId());

        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            if(memberProductCart.isEmpty()){
                CreateProductCart(member, item, requestProduct);
            }
            log.info(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
            AddProductItem(memberProductCart.get(), item, requestProduct);

        } else if (item.getItemCategoryType() == ItemCategoryType.SIDE) {
            if(memberSideProductCart.isEmpty()){
                CreateSideProductCart(member, item, requestSideProduct);
            }
            log.info(member.getNickname()+" 님의 sideProduct 카트는 이미 생성되어 있습니다.");
            AddSideProductItem(memberSideProductCart.get(), item, requestSideProduct);
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

    private void CreateSideProductCart(Member member, CartRegisterRequest item, SideProduct requestProduct ){

        SideProductCart firstCart = SideProductCart.builder().member(member).build();

        sideProductCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 sideProduct 카트를 생성하였습니다.");

        SideProductItem newSideProductItem = item.toSideProductItem(requestProduct, firstCart);
        sideProductItemRepository.save(newSideProductItem);

        log.info(member.getNickname()+" 님의 sideProduct 카트에 첫 상품을 추가하였습니다.");
    }

    private void AddSideProductItem(SideProductCart memberCart, CartRegisterRequest item, SideProduct requestProduct){

        Optional<SideProductItem> maybeSideProductItem =
                sideProductItemRepository.findBySideProduct_sideProductIdAndSideProductCart_Id(memberCart.getId(), item.getItemId());

        if (maybeSideProductItem.isPresent()) {
            SideProductItem sideProductItem = maybeSideProductItem.get();

            sideProductItem.setQuantity(sideProductItem.getQuantity() + item.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            log.info(requestProduct.getTitle() + " 상품의 수량을 sideProduct 카트에 추가하였습니다.");

        } else {
            SideProductItem newProductItem = item.toSideProductItem(requestProduct, memberCart);

            sideProductItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 sideProduct 카트에 추가하였습니다.");
        }
    }

    public List<CartItemListResponse> cartItemList(Long memberId){
    // repo 에서 items 를 찾기 > 컬렉션 객체를 스트림으로 처리 > 스트림의 각 요소를 다른 형대로 변환
        List<CartItemListResponse> cartItems =
            productItemRepository.findByProductCart_Member_memberId(memberId)
                // 컬렉션 객체를 스트림으로 처리
                .stream()
                // 스트림의 각 요소를 다른 형태의 요소로 변환 > productItem 의 필드를 이용해 CartItemListResponse 객체 생성
                .map(productItem -> new CartItemListResponse(productItem.getId(), productItem.getQuantity()))
                .collect(Collectors.toList());

        cartItems.addAll(
            sideProductItemRepository.findBySideProductCart_Member_memberId(memberId)
                .stream()
                .map(sideProductItem -> new CartItemListResponse(sideProductItem.getId(), sideProductItem.getQuantity()))
                .collect(Collectors.toList()));

        return cartItems;
    }

    private void changeCartItemQuantity(){

    }

}
