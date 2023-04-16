package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.order.controller.request.CartRegisterRequest;
import com.example.demo.domain.order.controller.request.SelfSaladCartRegisterForm;
import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.order.entity.SelfSaladCart;
import com.example.demo.domain.order.entity.SideProductCart;
import com.example.demo.domain.order.entity.items.*;
import com.example.demo.domain.order.repository.*;
import com.example.demo.domain.order.service.request.SelfSaladRequest;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.Amount;
import com.example.demo.domain.selfSalad.entity.Ingredient;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import com.example.demo.domain.selfSalad.entity.SelfSaladIngredient;
import com.example.demo.domain.selfSalad.repository.AmountRepository;
import com.example.demo.domain.selfSalad.repository.IngredientRepository;
import com.example.demo.domain.selfSalad.repository.SelfSaladIngredientRepository;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    final private ProductCartRepository productCartRepository;
    final private SideProductCartRepository sideProductCartRepository;
    final private SelfSaladCartRepository  selfSaladCartRepository;

    final private ProductItemRepository productItemRepository;
    final private SideProductItemRepository sideProductItemRepository;
    final private SelfSaladItemRepository selfSaladItemRepository;

    final private ProductsRepository productsRepository;
    final private SideProductsRepository sideProductsRepository;
    final private IngredientRepository ingredientRepository;
    final private AmountRepository amountRepository;
    final private SelfSaladIngredientRepository selfSaladIngredientRepository;
    final private SelfSaladRepository selfSaladRepository;

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
    public Integer classifyItemCategory(CartRegisterRequest item){

        Member member = requireNonNull(checkMember(item.getMemberId()));

        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {

            if(productCartRegister(item, member)){return 1;}

        } else if (item.getItemCategoryType() == ItemCategoryType.SIDE) {
            if(sideProductCartRegister(item, member)){{return 1;}};
        }
        return 0;
    }
    private Boolean productCartRegister(CartRegisterRequest item, Member member){

        Product requestProduct = requireNonNull(checkProduct(item.getItemId()));

        Optional<ProductCart> myProductCart =
                productCartRepository.findByMember_memberId(member.getMemberId());

        if(myProductCart.isEmpty()){
            createProductCart(member, item, requestProduct);
        }else{
            log.info(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
            if(addProductItemOrPlusQuantity(myProductCart.get(), item, requestProduct)){
                log.info(requestProduct.getTitle() + " 상품에 수량을 더하였습니다.");
                return true;
            }
        }
        return false;
    }

    private void createProductCart(Member member, CartRegisterRequest cartItem, Product requestProduct ){

        ProductCart firstCart = ProductCart.builder().member(member).build();

        productCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 product 카트를 생성하였습니다.");

        ProductItem newProductItem = cartItem.toProductItem(requestProduct, firstCart);
        productItemRepository.save(newProductItem);

        log.info(member.getNickname()+" 님의 product 카트에 첫 상품을 추가하였습니다.");
    }

    private Boolean addProductItemOrPlusQuantity(ProductCart myCart, CartRegisterRequest cartItem, Product requestProduct){

        Optional<ProductItem> maybeProductItem =
                productItemRepository.findByProduct_productIdAndProductCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeProductItem.isPresent()) {
            ProductItem productItem = maybeProductItem.get();

            productItem.setQuantity(productItem.getQuantity() + cartItem.getQuantity());
            productItemRepository.save(productItem);
            return true;

        } else {
            ProductItem newProductItem = cartItem.toProductItem(requestProduct, myCart);

            productItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
            return false;
        }
    }

    private Boolean sideProductCartRegister(CartRegisterRequest item, Member member){

        SideProduct requestSideProduct = requireNonNull(checkSideProduct(item.getItemId()));

        Optional<SideProductCart> mySideProductCart =
                sideProductCartRepository.findByMember_memberId(member.getMemberId());

        if(mySideProductCart.isEmpty()){
            createSideProductCart(member, item, requestSideProduct);
        }else{
            log.info(member.getNickname()+" 님의 sideProduct 카트는 이미 생성되어 있습니다.");
            if(addSideProductItemOrPlusQuantity(mySideProductCart.get(), item, requestSideProduct)){
                log.info(requestSideProduct.getTitle() + " 상품에 수량을 더하였습니다.");
                return true;
            }
        }
        return false;
    }

    private void createSideProductCart(Member member, CartRegisterRequest cartItem, SideProduct requestProduct ){

        SideProductCart firstCart = SideProductCart.builder().member(member).build();

        sideProductCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 sideProduct 카트를 생성하였습니다.");

        SideProductItem newSideProductItem = cartItem.toSideProductItem(requestProduct, firstCart);
        sideProductItemRepository.save(newSideProductItem);

        log.info(member.getNickname()+" 님의 sideProduct 카트에 첫 상품을 추가하였습니다.");
    }

    private Boolean addSideProductItemOrPlusQuantity(SideProductCart myCart, CartRegisterRequest cartItem, SideProduct requestProduct){

        Optional<SideProductItem> maybeSideProductItem =
                sideProductItemRepository.findBySideProduct_sideProductIdAndSideProductCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeSideProductItem.isPresent()) {
            SideProductItem sideProductItem = maybeSideProductItem.get();

            sideProductItem.setQuantity(sideProductItem.getQuantity() + cartItem.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            return true;

        } else {
            SideProductItem newProductItem = cartItem.toSideProductItem(requestProduct, myCart);

            sideProductItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 sideProduct 카트에 추가하였습니다.");
            return false;
        }
    }


    @Override
    public List<CartItemListResponse> cartItemList(Long memberId){

        List<ProductItem> productItems = productItemRepository.findByProductCart_Member_memberId(memberId);
        List<SideProductItem> sideProductItems = sideProductItemRepository.findBySideProductCart_Member_memberId(memberId);

        List<CartItemListResponse> cartItems = new ArrayList<>();
        if(!productItems.isEmpty()){
            for (ProductItem productItem : productItems) {
                cartItems.add(new CartItemListResponse(productItem));
            }
        }
        if(!sideProductItems.isEmpty()){
            for (SideProductItem sideProductItem : sideProductItems) {
                cartItems.add(new CartItemListResponse(sideProductItem));
            }
        }
        if(!cartItems.isEmpty()){
            // 2. Comparator 구현하여 Stream의 sorted() 메서드에 전달
            Comparator<CartItemListResponse> byDate = Comparator.comparing(CartItemListResponse::getAddedDate);
            // 3. Stream으로 변환 후 정렬
            List<CartItemListResponse> sortedItems = cartItems.stream()
                    .sorted(byDate)
                    .collect(Collectors.toList());

            return sortedItems;
        }
        return null;
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

        } else if (itemRequest.getItemCategoryType() == ItemCategoryType.SELF_SALAD) {
            SelfSaladItem selfSaladItem =
                    selfSaladItemRepository.findById(itemRequest.getItemId()).get();

            selfSaladItem.setQuantity(itemRequest.getQuantity());
            selfSaladItemRepository.save(selfSaladItem);
            log.info(selfSaladItem.getId()+" 번의 SelfSalad Item 의 수량이 변경되었습니다.");
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


    @Override
    public Integer checkSelfSaladCartLimit(Long memberId){

        Member member = requireNonNull(checkMember(memberId));

        Optional<SelfSaladCart> mySelfSaladCart =
                selfSaladCartRepository.findByMember_memberId(member.getMemberId());

        if(mySelfSaladCart.isPresent()){
            Integer selfSaladItemCount =
                    selfSaladItemRepository.countBySelfSaladCart_id(mySelfSaladCart.get().getId());

            if(selfSaladItemCount == CartItemLimit.SELF_SALAD.getMaxCount()){
                log.info("SelfSalad 카트가 꽉 찼습니다.");
                return 1;
            }
        }
        return 0;
    }

    private Map<Long, Ingredient> checkIngredients(SelfSaladCartRegisterForm requestForm ){

        List<Long> ingredientIds = new ArrayList<>();
        for(SelfSaladRequest ingredient : requestForm.getSelfSaladRequestList()){

            ingredientIds.add(ingredient.getIngredientId());
        }
        Optional <List<Ingredient>> maybeIngredients =
                ingredientRepository.findByIdIn(ingredientIds);

        if(maybeIngredients.isPresent()){
            log.info("Ingredients "+ingredientIds+" 번의 재료들이 존재합니다.");

            Map<Long, Ingredient> ingredientMap = new HashMap<>();

            for (Ingredient ingredient : maybeIngredients.get()) {
                ingredientMap.put(ingredient.getId(), ingredient);
            }
            return ingredientMap;
        }
        return null;
    }
    @Override
    public void selfSaladCartRegister(SelfSaladCartRegisterForm reqForm){

        Member member = requireNonNull(checkMember(reqForm.getMemberId()));
        Map<Long, Ingredient> ingredientMap = requireNonNull(checkIngredients(reqForm));

        Optional<SelfSaladCart> mySelfSaladCart =
                selfSaladCartRepository.findByMember_memberId(member.getMemberId());

        if(mySelfSaladCart.isEmpty()){
            SelfSaladCart firstCart =
                    createSelfSaladCart(member);
            addSelfSaladItem(firstCart, reqForm, ingredientMap);
            log.info(member.getNickname()+" 님의 SelfSalad 카트에 첫 상품을 추가하였습니다.");
        }else{
            log.info(member.getNickname()+" 님의 SelfSalad 카트는 이미 생성되어 있습니다.");

            addSelfSaladItem(mySelfSaladCart.get(), reqForm, ingredientMap);
            log.info(member.getNickname()+" 님의 SelfSalad 카트에 상품을 추가하였습니다.");
        }
    }

    private SelfSaladCart createSelfSaladCart(Member member){
        SelfSaladCart firstCart = SelfSaladCart.builder()
                .member(member)
                .build();
        selfSaladCartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 SelfSalad 카트를 생성하였습니다.");
        return firstCart;
    }

    private void addSelfSaladItem(SelfSaladCart myCart, SelfSaladCartRegisterForm reqForm,
                                  Map<Long, Ingredient> ingredientMap){
        // SelfSalad 저장
        SelfSalad selfSalad = reqForm.toSelfSalad();
        System.out.println("샐러드 출력 가즈아!"+ selfSalad);
        selfSaladRepository.save(selfSalad);

        // SelfSaladIngredient 저장
        List<SelfSaladIngredient> saladIngredients = new ArrayList<>();

        for (SelfSaladRequest request : reqForm.getSelfSaladRequestList()) {
            Amount amount =
                    amountRepository.findByAmountType(request.getAmountType()).get();
            Ingredient ingredient =
                    ingredientMap.get(request.getIngredientId());

            saladIngredients.add( request.toSelfSaladIngredient( selfSalad,ingredient, amount) );
        }
        selfSaladIngredientRepository.saveAll(saladIngredients);

        // SelfSalad Item
        SelfSaladItem newSelfSaladitem = reqForm.toSelfSaladItem(myCart, selfSalad);
        selfSaladItemRepository.save(newSelfSaladitem);
    }


}
