package com.example.demo.domain.cart.service;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelfSaladReadResponse;
import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.cart.entity.cartItems.*;
import com.example.demo.domain.cart.repository.CartRepository;
import com.example.demo.domain.cart.repository.ProductItemRepository;
import com.example.demo.domain.cart.repository.SelfSaladItemRepository;
import com.example.demo.domain.cart.repository.SideProductItemRepository;
import com.example.demo.domain.cart.service.request.SelfSaladRequest;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.service.MemberServiceImpl;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    final private CartRepository cartRepository;

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
    final private MemberServiceImpl memberService;

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
    public Integer classifyItemCategory(CartRegisterRequest cartItem){
    try{
        Member member = requireNonNull(memberService.checkMember(cartItem.getMemberId()));

        Optional<Cart> myCart =
                cartRepository.findByMember_memberId(member.getMemberId());

            if(myCart.isEmpty()){
                // 장바구니 생성
                Cart firstCart = createCart(member);
                if(addCartItem(firstCart, cartItem)){ return 1;}
                log.info(member.getNickname()+" 님의 장바구니에 첫 상품을 추가하였습니다.");

            }else{
                log.info(member.getNickname()+" 님의 장바구니는 이미 생성되어 있습니다.");
                if(addCartItem(myCart.get(), cartItem)){ return 1;}
            }
            return 0;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    private Boolean addCartItem(Cart myCart, CartRegisterRequest cartItem) {
        if (cartItem.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            // 장바구니 아이템 등록
            if(addProductItemOrPlusQuantity(myCart, cartItem).equals("아이템 수량추가")){
                return true;
            }

        } else if (cartItem.getItemCategoryType() == ItemCategoryType.SIDE) {

            if(addSideProductItemOrPlusQuantity(myCart, cartItem).equals("아이템 수량추가")){
                return true;
            }
        }
        return false;
    }
    private Cart createCart(Member member){

        Cart firstCart = Cart.builder().member(member).build();

        cartRepository.save(firstCart);
        log.info(member.getNickname()+" 님의 장바구니를 생성하였습니다.");
        return firstCart;
    }

    private String addProductItemOrPlusQuantity(Cart myCart, CartRegisterRequest cartItem){

        Product requestProduct = requireNonNull(checkProduct(cartItem.getItemId()));

        Optional<ProductItem> maybeProductItem =
                productItemRepository.findByProduct_productIdAndCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeProductItem.isPresent()) {
            ProductItem productItem = maybeProductItem.get();

            productItem.setQuantity(productItem.getQuantity() + cartItem.getQuantity());
            productItemRepository.save(productItem);
            return "아이템 수량추가";

        } else {
            ProductItem newProductItem = cartItem.toProductItem(requestProduct, myCart);

            productItemRepository.save(newProductItem);
            log.info(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
            return "새 아이템 추가";
        }
    }

    private String addSideProductItemOrPlusQuantity(Cart myCart, CartRegisterRequest cartItem){

        SideProduct requestSideProduct = requireNonNull(checkSideProduct(cartItem.getItemId()));

        Optional<SideProductItem> maybeSideProductItem =
                sideProductItemRepository.findBySideProduct_sideProductIdAndCart_Id(cartItem.getItemId(),myCart.getId());

        if (maybeSideProductItem.isPresent()) {
            SideProductItem sideProductItem = maybeSideProductItem.get();

            sideProductItem.setQuantity(sideProductItem.getQuantity() + cartItem.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            return "아이템 수량추가";

        } else {
            SideProductItem newProductItem = cartItem.toSideProductItem(requestSideProduct, myCart);

            sideProductItemRepository.save(newProductItem);
            log.info(requestSideProduct.getTitle() + " 상품을 sideProduct 카트에 추가하였습니다.");
            return "새 아이템 추가";
        }
    }


    @Override
    @Transactional
    public List<CartItemListResponse> cartItemList(Long memberId){

        List<ProductItem> productItems = productItemRepository.findByCart_Member_memberId(memberId);
        List<SideProductItem> sideProductItems = sideProductItemRepository.findByCart_Member_memberId(memberId);
        List<SelfSaladItem> selfSaladItems = selfSaladItemRepository.findByCart_Member_memberId(memberId);

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
        if(!selfSaladItems.isEmpty()){
            for (SelfSaladItem selfSaladItem : selfSaladItems) {
                cartItems.add(new CartItemListResponse(selfSaladItem));
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

            sideProductItem.setQuantity(sideProductItem.getQuantity() + itemRequest.getQuantity());
            sideProductItemRepository.save(sideProductItem);
            log.info(sideProductItem.getId()+" 번의 SideProduct Item 의 수량이 변경되었습니다.");

        } else if (itemRequest.getItemCategoryType() == ItemCategoryType.SELF_SALAD) {
            SelfSaladItem selfSaladItem =
                    selfSaladItemRepository.findById(itemRequest.getItemId()).get();

            selfSaladItem.setQuantity(selfSaladItem.getQuantity() + itemRequest.getQuantity());
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

        } else if (itemDelete.getItemCategoryType() == ItemCategoryType.SELF_SALAD) {
            SelfSalad deleteSalad =
                    selfSaladItemRepository.findById(itemDelete.getItemId()).get().getSelfSalad();

            selfSaladItemRepository.deleteById(itemDelete.getItemId());
            log.info(itemDelete.getItemId()+" 번 SelfSalad Item 이 삭제되었습니다.");
            selfSaladRepository.deleteById(deleteSalad.getId());

            log.info(itemDelete.getItemId()+" 번 SelfSalad 가 삭제되었습니다.");
        }
    }


    @Override
    public Integer checkSelfSaladCartLimit(Long memberId){
        try {
            Member member = requireNonNull(memberService.checkMember(memberId));

            Optional<Cart> myCart =
                    cartRepository.findByMember_memberId(member.getMemberId());

            if(myCart.isPresent()){
                Integer selfSaladItemCount =
                        selfSaladItemRepository.countByCart_id(myCart.get().getId());

                if(selfSaladItemCount == CartItemLimit.SELF_SALAD.getMaxCount()){
                    log.info("SelfSalad 카트가 꽉 찼습니다.");
                    return 1;
                }
            }
            return 0;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    private Map<Long, Ingredient> checkIngredients( Set<Long> ingredientIds ){

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
    public void selfSaladCartRegister(SelfSaladCartRegisterForm reqForm) {

        try {
            Member member = requireNonNull(memberService.checkMember(reqForm.getMemberId()));

            Set<Long> ingredientIds = new HashSet<>();
            for (SelfSaladRequest ingredient : reqForm.getSelfSaladRequestList()) {

                ingredientIds.add(ingredient.getIngredientId());
            }
            Map<Long, Ingredient> ingredientMap = requireNonNull(checkIngredients(ingredientIds));

            Optional<Cart> maybeCart =
                    cartRepository.findByMember_memberId(member.getMemberId());

            Cart myCart;
            if (maybeCart.isEmpty()) {
                myCart = createCart(member);
            } else {
                log.info(member.getNickname() + " 님의 SelfSalad 장바구니는 이미 생성되어 있습니다.");
                myCart = maybeCart.get();
            }
            SelfSalad mySalad = createSelfSalad(reqForm);

            addSelfSaladIngredient(mySalad,reqForm.getSelfSaladRequestList(), ingredientMap );

            addSelfSaladItem(myCart, mySalad, reqForm);
            log.info(member.getNickname() + " 님의 장바구니에 첫 상품을 추가하였습니다.");

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
        }
    }

    private SelfSalad createSelfSalad(SelfSaladCartRegisterForm reqForm){
        SelfSalad mySalad = new SelfSalad(reqForm.getTitle(),
                                    reqForm.getTotalPrice(),
                                    reqForm.getTotalCalorie());
        selfSaladRepository.save(mySalad);
        System.out.println("샐러드 출력 가즈아!"+ mySalad);
        return mySalad;
    }


    private void addSelfSaladItem(Cart myCart, SelfSalad mySalad, SelfSaladCartRegisterForm reqForm){
        // SelfSalad Item
        SelfSaladItem newSelfSaladitem = reqForm.toSelfSaladItem(myCart, mySalad);
        selfSaladItemRepository.save(newSelfSaladitem);
    }

    private void addSelfSaladIngredient(SelfSalad mySalad, List<SelfSaladRequest> requestList,
                                        Map<Long, Ingredient> ingredientMap){
        // SelfSaladIngredient 저장
        List<SelfSaladIngredient> saladIngredients = new ArrayList<>();

        for (SelfSaladRequest request : requestList) {
            Amount amount =
                    amountRepository.findByAmountType(request.getAmountType()).get();
            Ingredient ingredient =
                    ingredientMap.get(request.getIngredientId());

            saladIngredients.add( request.toSelfSaladIngredient( mySalad, ingredient, amount) );
        }
        selfSaladIngredientRepository.saveAll(saladIngredients);
    }

    @Override
    public List<SelfSaladReadResponse> readSelfSaladIngredient(Long itemId){
        // 장바구니 수정 요청시 보낼 샐러드_재료 데이터
        Optional<SelfSaladItem> maybeItem = selfSaladItemRepository.findById(itemId);

        if(maybeItem.isPresent()){
            // Self Salad 찾기
            Long selfSaladId = maybeItem.get().getSelfSalad().getId();
            List<SelfSaladIngredient> selfSaladIngredients =
                    selfSaladIngredientRepository.findBySelfSalad_id(selfSaladId);

            List<SelfSaladReadResponse> responseList = new ArrayList<>();
            for(SelfSaladIngredient ingredient : selfSaladIngredients){
                responseList.add(
                        new SelfSaladReadResponse(ingredient.getIngredient().getId(),
                                                  ingredient.getSelectedAmount()));
            }
            return responseList;
        }
        return null;
    }
    @Override
    @Transactional
    public void modifySelfSaladItem(Long itemId, SelfSaladModifyForm modifyForm) {
        // selfSalad item 확인
        Optional<SelfSaladItem> maybeItem = selfSaladItemRepository.findById(itemId);
        if(maybeItem.isPresent()){
            // Self Salad 찾기
            SelfSalad mySalad = maybeItem.get().getSelfSalad();
            modifySelfSalad(mySalad,
                            modifyForm.getTotalPrice(),
                            modifyForm.getTotalCalorie());
            modifySelfSaladIngredient(mySalad, modifyForm.getSelfSaladRequestList());
        }
    }

    private void modifySelfSalad(SelfSalad mySalad, Long price, Long calorie){
        // 수정
        mySalad.setTotal(price, calorie);
        selfSaladRepository.save(mySalad);
    }

    private void modifySelfSaladIngredient(SelfSalad mySalad, List<SelfSaladRequest> requestItems){
        // 수정 전 재료들 [재료 id : 샐러드_재료]
        Map<Long, SelfSaladIngredient> prevIngredients =
                selfSaladIngredientRepository.findBySelfSalad_id(mySalad.getId()).stream()
                        .collect(Collectors.toMap(
                                selfSaladIngredient -> selfSaladIngredient.getIngredient().getId(),
                                selfSaladIngredient -> selfSaladIngredient
                        ));
        // 수정 요청 [재료 id : 샐려드_재료 요청]
        Map<Long, SelfSaladRequest> reqIngredients = requestItems.stream()
                .collect(Collectors.toMap(SelfSaladRequest::getIngredientId,
                                          Function.identity()));
        // 1. 공통된 Ingredient ID 만 포함하는 Set (수량 수정)
        Set<Long> commonIds = requestItems.stream()
                .map(SelfSaladRequest::getIngredientId)
                .collect(Collectors.toSet());
        commonIds.retainAll(prevIngredients.keySet());

        // 2. 새롭게 추가된 Ingredient ID 만 포함하는 Set - HashSet 은 중복된 값이 없는 집합을 저장
        Set<Long> newIngredientIds = new HashSet<>(reqIngredients.keySet());
        newIngredientIds.removeAll(commonIds);

        // 3. 삭제해야 하는 Ingredient ID 만 포함하는 Set (수정 후 요청객체에 없는 수정전 샐러드_재료)
        Set<Long> deleteIngredientIds = prevIngredients.values().stream()
                .filter(ingredient -> !commonIds.contains(ingredient.getIngredient().getId()))
                .map(ingredient -> ingredient.getIngredient().getId())
                .collect(Collectors.toSet());

        // 1. 수량 수정
        if( ! commonIds.isEmpty()){
            modifySelectedAmount(prevIngredients, reqIngredients, commonIds);
        }
        // 2. 새로운 재료 추가
        if( ! newIngredientIds.isEmpty()){
            modifyAddSelfSaladIngredient(requestItems, newIngredientIds, mySalad);
        }
        // 3. 샐러드_재료 삭제
        if( ! deleteIngredientIds.isEmpty()){
            deleteSelfSaladIngredient(mySalad.getId(), deleteIngredientIds);
        }
    }

    private boolean modifySelectedAmount (Map<Long, SelfSaladIngredient> prevIngredients,
                                          Map<Long, SelfSaladRequest> reqIngredients,
                                          Set<Long> commonIds){
        log.info("수량 변경 요청 온 샐러드 재료 IDs : "+commonIds);
        List<SelfSaladIngredient> modifies = new ArrayList<>();
        for (Long commonId : commonIds) {
            SelfSaladIngredient prevIngredient = prevIngredients.get(commonId);

            SelfSaladRequest reqIngredient = reqIngredients.get(commonId);
            if( prevIngredient.getSelectedAmount() != reqIngredient.getSelectedAmount()){
                prevIngredient.setSelectedAmount(reqIngredient.getSelectedAmount());
                modifies.add(prevIngredient);
            }
        }
        selfSaladIngredientRepository.saveAll(modifies);
        return true;
    }

    private boolean modifyAddSelfSaladIngredient(List<SelfSaladRequest> requestItems,
                                                 Set<Long> newIngredientIds,
                                                 SelfSalad prevSalad){
        log.info("새롭게 추가할 샐러드 재료 IDs : "+newIngredientIds);
        List<SelfSaladRequest> addIngredients = requestItems.stream()
                .filter(item -> newIngredientIds.contains(item.getIngredientId()))
                .collect(Collectors.toList());

        Map<Long, Ingredient> ingredientMap = checkIngredients(newIngredientIds);

        addSelfSaladIngredient(prevSalad, addIngredients, ingredientMap);
        return true;
    }

    private boolean deleteSelfSaladIngredient(Long saladId, Set<Long> deleteIngredientIds){
        log.info("삭제할 샐러드_재료 IDs: "+deleteIngredientIds);
        selfSaladIngredientRepository.deleteAllBySelfSalad_IdAndIngredient_IdIn
                (saladId, deleteIngredientIds);
        return true;
    }
}
