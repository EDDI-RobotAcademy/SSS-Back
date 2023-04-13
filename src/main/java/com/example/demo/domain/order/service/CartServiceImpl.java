package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.order.controller.request.CartRegisterRequest;
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
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public void classifyItemCategory(CartRegisterRequest item){

        Member member = requireNonNull(checkMember(item.getMemberId()));

        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            productCartRegister(item, member);

        } else if (item.getItemCategoryType() == ItemCategoryType.SIDE) {
            sideProductCartRegister(item, member);
        }
    }
    private void productCartRegister(CartRegisterRequest item, Member member){

        Product requestProduct = requireNonNull(checkProduct(item.getItemId()));

        Optional<ProductCart> myProductCart =
                productCartRepository.findByMember_memberId(member.getMemberId());

        if(myProductCart.isEmpty()){
            createProductCart(member, item, requestProduct);
        }else{
            log.info(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
            addProductItem(myProductCart.get(), item, requestProduct);
        }
    }

    private void createProductCart(Member member, CartRegisterRequest cartItem, Product requestProduct ){

        ProductCart firstCart = ProductCart.builder().member(member).build();

        productCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 product 카트를 생성하였습니다.");

        ProductItem newProductItem = cartItem.toProductItem(requestProduct, firstCart);
        productItemRepository.save(newProductItem);

        log.info(member.getNickname()+" 님의 product 카트에 첫 상품을 추가하였습니다.");
    }

    private void addProductItem(ProductCart myCart, CartRegisterRequest cartItem, Product requestProduct){

        Optional<ProductItem> maybeProductItem =
                productItemRepository.findByProduct_productIdAndProductCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeProductItem.isPresent()) {
            ProductItem productItem = maybeProductItem.get();

            productItem.setQuantity(productItem.getQuantity() + cartItem.getQuantity());
            productItemRepository.save(productItem);
            log.info(requestProduct.getTitle() + " 상품의 수량을 카트에 추가하였습니다.");

        } else {
            ProductItem newProductItem = cartItem.toProductItem(requestProduct, myCart);

            productItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
        }
    }

    private void sideProductCartRegister(CartRegisterRequest item, Member member){

        SideProduct requestSideProduct = requireNonNull(checkSideProduct(item.getItemId()));

        Optional<SideProductCart> mySideProductCart =
                sideProductCartRepository.findByMember_memberId(member.getMemberId());

        if(mySideProductCart.isEmpty()){
            createSideProductCart(member, item, requestSideProduct);
        }else{
            log.info(member.getNickname()+" 님의 sideProduct 카트는 이미 생성되어 있습니다.");
            addSideProductItem(mySideProductCart.get(), item, requestSideProduct);
        }
    }

    private void createSideProductCart(Member member, CartRegisterRequest cartItem, SideProduct requestProduct ){

        SideProductCart firstCart = SideProductCart.builder().member(member).build();

        sideProductCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 sideProduct 카트를 생성하였습니다.");

        SideProductItem newSideProductItem = cartItem.toSideProductItem(requestProduct, firstCart);
        sideProductItemRepository.save(newSideProductItem);

        log.info(member.getNickname()+" 님의 sideProduct 카트에 첫 상품을 추가하였습니다.");
    }

    private void addSideProductItem(SideProductCart myCart, CartRegisterRequest cartItem, SideProduct requestProduct){

        Optional<SideProductItem> maybeSideProductItem =
                sideProductItemRepository.findBySideProduct_sideProductIdAndSideProductCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeSideProductItem.isPresent()) {
            SideProductItem sideProductItem = maybeSideProductItem.get();

            sideProductItem.setQuantity(sideProductItem.getQuantity() + cartItem.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            log.info(requestProduct.getTitle() + " 상품의 수량을 sideProduct 카트에 추가하였습니다.");

        } else {
            SideProductItem newProductItem = cartItem.toSideProductItem(requestProduct, myCart);

            sideProductItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 sideProduct 카트에 추가하였습니다.");
        }
    }

    public List<CartItemListResponse> cartItemList(Long memberId){

        List<ProductItem> productItems = productItemRepository.findByProductCart_Member_memberId(memberId);
        List<SideProductItem> sideProductItems = sideProductItemRepository.findBySideProductCart_Member_memberId(memberId);

        List<CartItemListResponse> cartItems = Stream.concat(
                productItems.stream().map(productItem -> new CartItemListResponse(
                        productItem.getId(),
                        productItem.getQuantity(),
                        productItem.getAddedDate(),
                        productItem.getProduct().getProductId(),
                        productItem.getProduct().getTitle(),
                        productItem.getProduct().getProductImgs().get(0).getEditedImg(),
                        productItem.getProduct().getPrice())),
                sideProductItems.stream().map(sideProductItem -> new CartItemListResponse(
                        sideProductItem.getId(),
                        sideProductItem.getQuantity(),
                        sideProductItem.getAddedDate(),
                        sideProductItem.getSideProduct().getSideProductId(),
                        sideProductItem.getSideProduct().getTitle(),
                        sideProductItem.getSideProduct().getSideProductImg().getEditedImg(),
                        sideProductItem.getSideProduct().getPrice())))
            .sorted(Comparator.comparing(CartItemListResponse::getAddedDate).reversed())
            .collect(Collectors.toList());

        return cartItems;
    }

    @Override
    public void modifyCartItemQuantity(CartItemQuantityModifyRequest itemRequest){
        if (itemRequest.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            ProductItem productItem =
                    productItemRepository.findById(itemRequest.getItemId()).get();

            productItem.setQuantity(productItem.getQuantity() + itemRequest.getQuantity());
            productItemRepository.save(productItem);
            log.info(productItem.getId()+" 번의 product Item 의 수량이 변경되었습니다.");

        } else if (itemRequest.getItemCategoryType() == ItemCategoryType.SIDE) {
            SideProductItem sideProductItem =
                    sideProductItemRepository.findById(itemRequest.getItemId()).get();

            sideProductItem.setQuantity(itemRequest.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            log.info(sideProductItem.getId()+" 번의 SideProduct Item 의 수량이 변경되었습니다.");
        }
    }
    @Override
    public void deleteCartItem(CartItemDeleteRequest itemDelete){

        if (itemDelete.getItemCategoryType() == ItemCategoryType.PRODUCT) {

            productItemRepository.deleteById(itemDelete.getItemId());
            log.info(itemDelete.getItemId()+" 번 product Item 이 삭제되었습니다.");

        } else if (itemDelete.getItemCategoryType() == ItemCategoryType.SIDE) {

            sideProductItemRepository.deleteById(itemDelete.getItemId());
            log.info(itemDelete.getItemId()+" 번 SideProduct Item 이 삭제되었습니다.");
        }
    }

}
