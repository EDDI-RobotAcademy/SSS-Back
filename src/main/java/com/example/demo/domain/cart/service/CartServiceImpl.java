package com.example.demo.domain.cart.service;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemIdAndCategory;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelfSaladReadResponse;
import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.cart.entity.cartItems.*;
import com.example.demo.domain.cart.repository.CartItemRepository;
import com.example.demo.domain.cart.repository.CartRepository;
import com.example.demo.domain.cart.service.request.SelfSaladRequest;
import com.example.demo.domain.member.entity.Member;
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
import com.example.demo.domain.utility.itemCategory.ItemCategoryType;
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
    final private CartItemRepository cartItemRepository;

    final private ProductsRepository productsRepository;
    final private SideProductsRepository sideProductsRepository;
    final private IngredientRepository ingredientRepository;
    final private AmountRepository amountRepository;
    final private SelfSaladIngredientRepository selfSaladIngredientRepository;
    final private SelfSaladRepository selfSaladRepository;

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

    private Cart createOrFindCart(Member member){
        Optional<Cart> myCart =
                cartRepository.findByMember_memberId(member.getMemberId());
        if(myCart.isPresent()){
            log.info(member.getNickname()+" 님의 장바구니는 이미 생성되어 있습니다.");
            return myCart.get();

        }else{
            Cart firstCart = Cart.builder().member(member).build();

            cartRepository.save(firstCart);
            log.info(member.getNickname()+" 님의 장바구니를 생성하였습니다.");
            return firstCart;
        }
    }
    @Override
    public Integer classifyItemCategory(Long memberId, CartRegisterRequest reqItem){
    try{
        Member member = requireNonNull(memberService.checkMember(memberId));

        Cart myCart = createOrFindCart(member);

            switch (reqItem.getItemCategoryType()){
                case PRODUCT:
                    addProductItem(myCart, reqItem); break;
                case SIDE:
                    addSideProductItem(myCart, reqItem); break;
                default:
                    throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : "
                                                        + reqItem.getItemCategoryType());
            }
        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
        }
    return 1;
    }
    private void addProductItem(Cart myCart, CartRegisterRequest reqItem){

        Product reqProduct = requireNonNull(checkProduct(reqItem.getItemId()));

        CartItem newCartItem = new ProductItem(
                reqItem.getQuantity(),
                reqProduct,
                myCart
        );
        cartItemRepository.save(newCartItem);
        log.info(reqProduct.getTitle() + " 상품을 장바구니에 추가하였습니다.");
    }

    private void addSideProductItem(Cart myCart, CartRegisterRequest reqItem){

        SideProduct reqSideProduct = requireNonNull(checkSideProduct(reqItem.getItemId()));

        CartItem newCartItem = new SideProductItem(
                reqItem.getQuantity(),
                reqSideProduct,
                myCart
        );
        cartItemRepository.save(newCartItem);
        log.info(reqSideProduct.getTitle() + " 상품을 장바구니에 추가하였습니다.");
    }


    @Override
    public Boolean isItemInCart(Long itemId, Long memberId, ItemCategoryType itemCategoryType){
        memberService.checkMember(memberId);

        Optional<Cart> myCart = cartRepository.findByMember_memberId(memberId);
        if(myCart.isPresent()) {
            switch (itemCategoryType) {
                case PRODUCT:
                    Optional<CartItem> productItem =
                            cartItemRepository.findByProduct_IdAndCart_Id(itemId, myCart.get().getId());
                    return productItem.isPresent();
                case SIDE:
                    Optional<CartItem> sideProductItem =
                            cartItemRepository.findBySideProduct_IdAndCart_Id(itemId, myCart.get().getId());
                    return sideProductItem.isPresent();
                default:
                    throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : " + itemCategoryType);
            }
        }
        return false;
    }

    @Override
    @Transactional
    public List<CartItemListResponse> cartItemList(Long memberId){

        Optional<List<CartItem>> cartItemList = cartItemRepository.findByCart_Member_memberId(memberId);

        List<CartItemListResponse> cartItems = new ArrayList<>();

        cartItemList.ifPresent(items -> {
            for (CartItem cartItem : items) {
                if (cartItem instanceof ProductItem) {
                    cartItems.add(new CartItemListResponse().toProductItem(cartItem));

                } else if (cartItem instanceof SelfSaladItem) {
                    cartItems.add(new CartItemListResponse().toSideProductItem(cartItem));

                } else if (cartItem instanceof SideProductItem) {
                    cartItems.add(new CartItemListResponse().toSelfSaladItem(cartItem));
                }
            }
        });
        if(!cartItems.isEmpty()){
            // 2. Comparator 구현하여 Stream의 sorted() 메서드에 전달
            Comparator<CartItemListResponse> byDate = Comparator.comparing(CartItemListResponse::getAddedDate);
            // 3. Stream으로 변환 후 정렬
            return cartItems.stream()
                    .sorted(byDate)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void modifyCartItemQuantity(CartItemQuantityModifyRequest reqItem){
        Optional<CartItem> cartItem =
                cartItemRepository.findById(reqItem.getItemId());
        if(cartItem.isPresent()){
            cartItem.get().setQuantity(reqItem.getQuantity() + reqItem.getQuantity());
            cartItemRepository.save(cartItem.get());
            log.info(cartItem.get().getId()+" 번의 cart Item 의 수량이 변경되었습니다.");
        }
    }

    @Override
    public void deleteCartItem(CartItemIdAndCategory itemDelete){
        // self salad 먼저 삭제 후 cart item 삭제
        SelfSalad deleteSalad;
        if(itemDelete.getItemCategoryType() == ItemCategoryType.SELF){
            deleteSalad =
                    cartItemRepository.findById(itemDelete.getItemId()).get().getSelfSalad();
            cartItemRepository.deleteById(itemDelete.getItemId());
            selfSaladRepository.deleteById(deleteSalad.getId());
        }
        cartItemRepository.deleteById(itemDelete.getItemId());
        log.info(itemDelete.getItemId()+" 번 cart Item 이 삭제되었습니다.");
    }

    @Override
    public void deleteCartItemList(List<CartItemIdAndCategory> deleteItemlist){

        List<Long> cartItems = new ArrayList<>();
        List<Long> selfSaladItems = new ArrayList<>();

        for(CartItemIdAndCategory deleteItem : deleteItemlist){
            switch (deleteItem.getItemCategoryType()) {
                case PRODUCT:
                case SIDE:
                    cartItems.add(deleteItem.getItemId()); break;
                case SELF:
                    selfSaladItems.add(deleteItem.getItemId()); break;
                default:
                    throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : " + deleteItem.getItemCategoryType());
            }
        }
        if( ! cartItems.isEmpty()){
            cartItemRepository.deleteAllByIdInBatch(cartItems);
            log.info(cartItems+" 번 Cart Item 들이 삭제되었습니다.");
        }
        if( ! selfSaladItems.isEmpty()){
            cartItemRepository.deleteAllByIdInBatch(selfSaladItems);
            log.info(selfSaladItems+" 번 SelfSalad Cart Item 들이 삭제되었습니다.");

            List<SelfSaladItem> selfSaladItemList = cartItemRepository.findByIdIn(selfSaladItems);
            List<SelfSalad> selfSalads = new ArrayList<>();
            for(SelfSaladItem selfSaladItem : selfSaladItemList){
                selfSalads.add(selfSaladItem.getSelfSalad());
            }
            selfSaladRepository.deleteAll(selfSalads);
            log.info(selfSaladItems+" 번 SelfSalad 들이 삭제되었습니다.");
        }
    }

    @Override
    public Integer checkSelfSaladCartLimit(Long memberId){
        try {
            Member member = requireNonNull(memberService.checkMember(memberId));

            Optional<Cart> myCart =
                    cartRepository.findByMember_memberId(member.getMemberId());

            if (myCart.isPresent()) {
                Optional<Integer> selfSaladItemCount =
                        cartItemRepository.countSelfSaladItemsByCartId(myCart.get().getId());

                if (selfSaladItemCount.isPresent() &&
                    selfSaladItemCount.get() == CartItemLimit.SELF_SALAD.getMaxCount()) {
                    log.info("SelfSalad 카트가 꽉 찼습니다.");
                    return 1;
                }
                return 0;
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
    @Override
    public void selfSaladCartRegister(Long memberId, SelfSaladCartRegisterForm reqForm) {

        try {
            Member member = requireNonNull(memberService.checkMember(memberId));

            Set<Long> ingredientIds = new HashSet<>();
            for (SelfSaladRequest ingredient : reqForm.getSelfSaladRequestList()) {

                ingredientIds.add(ingredient.getIngredientId());
            }
            Map<Long, Ingredient> ingredientMap = requireNonNull(checkIngredients(ingredientIds));

            Cart myCart = createOrFindCart(member);

            SelfSalad mySalad = createSelfSalad(reqForm);

            addSelfSaladIngredient(mySalad,reqForm.getSelfSaladRequestList(), ingredientMap );

            addSelfSaladItem(myCart, mySalad, reqForm);
            log.info(member.getNickname() + " 님의 장바구니에 셀프 샐러드 상품을 추가하였습니다.");

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
        CartItem newCartItem = new SelfSaladItem(
                reqForm.getQuantity(),
                mySalad,
                myCart
        );
        cartItemRepository.save(newCartItem);
    }

    private void addSelfSaladIngredient(SelfSalad mySalad, List<SelfSaladRequest> reqList,
                                        Map<Long, Ingredient> ingredientMap){
        // SelfSaladIngredient 저장
        List<SelfSaladIngredient> saladIngredients = new ArrayList<>();

        for (SelfSaladRequest request : reqList) {
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
        Optional<CartItem> maybeItem = cartItemRepository.findById(itemId);

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
        Optional<CartItem> maybeItem = cartItemRepository.findById(itemId);
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

    private void modifySelfSaladIngredient(SelfSalad mySalad, List<SelfSaladRequest> reqItems){
        // 수정 전 재료들 [재료 id : 샐러드_재료]
        Map<Long, SelfSaladIngredient> prevIngredients =
                selfSaladIngredientRepository.findBySelfSalad_id(mySalad.getId()).stream()
                        .collect(Collectors.toMap(
                                selfSaladIngredient -> selfSaladIngredient.getIngredient().getId(),
                                selfSaladIngredient -> selfSaladIngredient
                        ));
        // 수정 요청 [재료 id : 샐려드_재료 요청]
        Map<Long, SelfSaladRequest> reqIngredients = reqItems.stream()
                .collect(Collectors.toMap(SelfSaladRequest::getIngredientId,
                                          Function.identity()));
        // 1. 공통된 Ingredient ID 만 포함하는 Set (수량 수정)
        Set<Long> commonIds = reqItems.stream()
                .map(SelfSaladRequest::getIngredientId)
                .collect(Collectors.toSet());
        commonIds.retainAll(prevIngredients.keySet());

        // 2. 삭제해야 하는 Ingredient ID 만 포함하는 Set (수정 후 요청객체에 없는 수정전 샐러드_재료)
        Set<Long> deleteIngredientIds = prevIngredients.values().stream()
                .filter(ingredient -> !commonIds.contains(ingredient.getIngredient().getId()))
                .map(ingredient -> ingredient.getIngredient().getId())
                .collect(Collectors.toSet());

        // 1. 수량 수정
        if( ! commonIds.isEmpty()){
            modifySelectedAmount(prevIngredients, reqIngredients, commonIds);
        }
        // 2. 샐러드_재료 삭제
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

    private boolean deleteSelfSaladIngredient(Long saladId, Set<Long> deleteIngredientIds){
        log.info("삭제할 샐러드_재료 IDs: "+deleteIngredientIds);
        selfSaladIngredientRepository.deleteAllBySelfSalad_IdAndIngredient_IdIn
                (saladId, deleteIngredientIds);
        return true;
    }
}
