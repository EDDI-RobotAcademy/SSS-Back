package com.example.demo.domain.cart.service;

import com.example.demo.domain.cart.controller.form.SelfSaladCartRegisterForm;
import com.example.demo.domain.cart.controller.form.SelfSaladModifyForm;
import com.example.demo.domain.cart.controller.request.CartItemIdAndCategory;
import com.example.demo.domain.cart.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.cart.controller.request.CartRegisterRequest;
import com.example.demo.domain.cart.controller.response.CartItemListResponse;
import com.example.demo.domain.cart.controller.response.SelectedIngredientsResponse;
import com.example.demo.domain.cart.entity.Cart;
import com.example.demo.domain.cart.entity.cartItems.*;
import com.example.demo.domain.cart.repository.CartItemRepository;
import com.example.demo.domain.cart.repository.CartRepository;
import com.example.demo.domain.cart.service.request.SelfSaladModifyRequest;
import com.example.demo.domain.cart.service.request.SelfSaladRequest;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
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
import com.example.demo.domain.utility.member.MemberUtils;
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

    final private MemberRepository memberRepository;

    private Cart getCartByMemberId(Long memberId){
        return cartRepository.findByMember_memberId(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 장바구니입니다. "));
    }
    private CartItem getCartItemById(Long itemId){
        return cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 장바구니 상품은 없습니다. : "+itemId));
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

        Member member = MemberUtils.getMemberById(memberRepository,memberId);
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
        return 1;
    }
    private void addProductItem(Cart myCart, CartRegisterRequest reqItem){

        Product reqProduct = productsRepository.findById(reqItem.getItemId())
                .orElseThrow(() -> new RuntimeException("등록된 상품이 아닙니다. : " + reqItem.getItemId()));

        CartItem newCartItem = new ProductItem(
                reqItem.getQuantity(),
                reqProduct,
                myCart
        );
        cartItemRepository.save(newCartItem);
        log.info(reqProduct.getTitle() + " 상품을 장바구니에 추가하였습니다.");
    }

    private void addSideProductItem(Cart myCart, CartRegisterRequest reqItem){

        SideProduct reqSideProduct = sideProductsRepository.findById(reqItem.getItemId())
                .orElseThrow(() -> new RuntimeException("해당 SideProduct 상품이 없습니다. : " + reqItem.getItemId()));

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

        MemberUtils.getMemberById(memberRepository,memberId);
        Cart myCart = getCartByMemberId(memberId);

        switch (itemCategoryType) {
            case PRODUCT:
                Optional<CartItem> productItem =
                        cartItemRepository.findByProduct_IdAndCart_Id(itemId, myCart.getId());
                return productItem.isPresent();
            case SIDE:
                Optional<CartItem> sideProductItem =
                        cartItemRepository.findBySideProduct_IdAndCart_Id(itemId, myCart.getId());
                return sideProductItem.isPresent();
            default:
                throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : " + itemCategoryType);
        }
    }

    @Override
    @Transactional
    public List<CartItemListResponse> cartItemList(Long memberId){

        Optional<List<CartItem>> cartItemList = cartItemRepository.findByCart_Member_memberId(memberId);

        List<CartItemListResponse> cartItems = new ArrayList<>();

        cartItemList.ifPresent(items -> {
            for (CartItem cartItem : items) {
                if (cartItem instanceof ProductItem) {
                    cartItems.add(new CartItemListResponse().toProductItem((ProductItem)cartItem));

                } else if (cartItem instanceof SideProductItem) {
                    cartItems.add(new CartItemListResponse().toSideProductItem((SideProductItem)cartItem));

                } else if (cartItem instanceof SelfSaladItem) {
                    cartItems.add(new CartItemListResponse().toSelfSaladItem((SelfSaladItem)cartItem));
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

        CartItem cartItem = getCartItemById(reqItem.getItemId());

        cartItem.modifyQuantity(reqItem.getQuantity());
        log.info(cartItem.getId()+" 번의 cart Item 의 수량이 변경되었습니다.");

        cartItemRepository.save(cartItem);

    }

    @Override
    public void deleteCartItem(CartItemIdAndCategory deleteItem){

        CartItem cartItem = getCartItemById(deleteItem.getItemId());

        ItemCategoryType itemType = deleteItem.getItemCategoryType();
            switch(itemType){
            case PRODUCT:
                ProductItem productItem = (ProductItem) cartItem;
                cartItemRepository.delete(productItem); break;
            case SIDE:
                SideProductItem sideProductItem = (SideProductItem) cartItem;
                cartItemRepository.delete(sideProductItem); break;
            case SELF:
                SelfSaladItem selfSaladItem = (SelfSaladItem) cartItem;
                Long selfSaladId = selfSaladItem.getSelfSalad().getId();
                cartItemRepository.delete(selfSaladItem);
                selfSaladRepository.deleteById(selfSaladId); break;
            default:
                throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : " + itemType);
            }
        log.info(deleteItem.getItemId() + " 번 cart Item 이 삭제되었습니다.");
    }

    @Override
    public void deleteCartItemList(List<CartItemIdAndCategory> deleteItemlist){

        List<Long> cartItemIds = new ArrayList<>();
        List<Long> saladItemIds = new ArrayList<>();

        for(CartItemIdAndCategory deleteItem : deleteItemlist) {
            switch (deleteItem.getItemCategoryType()) {
                case PRODUCT:
                case SIDE:
                    cartItemIds.add(deleteItem.getItemId()); break;
                case SELF:
                    saladItemIds.add(deleteItem.getItemId()); break;
                default:
                    throw new IllegalArgumentException("존재하지 않는 장바구니 카테고리 입니다. : "
                            + deleteItem.getItemCategoryType());
            }
        }
        if( ! cartItemIds.isEmpty()){
            cartItemRepository.deleteAllByIdInBatch(cartItemIds);
            log.info(cartItemIds+" 번 Cart Item 들이 삭제되었습니다.");
        }
        List<SelfSalad> selfSalads = new ArrayList<>();
        if( ! saladItemIds.isEmpty()){
            List<SelfSaladItem> saladItem = cartItemRepository.findByIdIn(saladItemIds);
            for(SelfSaladItem item : saladItem){
                selfSalads.add(item.getSelfSalad());
            }
            cartItemRepository.deleteAllByIdInBatch(saladItemIds);
            log.info(saladItemIds+" 번 SelfSalad Cart Item 들이 삭제되었습니다.");

            selfSaladRepository.deleteAll(selfSalads);
            log.info(" SelfSalad 가 삭제되었습니다.");
        }
    }

    @Override
    public Integer checkSelfSaladCartLimit(Long memberId){
        MemberUtils.getMemberById(memberRepository,memberId);
        Cart myCart = getCartByMemberId(memberId);

        Optional<Integer> selfSaladItemCount =
                cartItemRepository.countSelfSaladItemsByCartId(myCart.getId());

        if (selfSaladItemCount.isPresent() &&
            selfSaladItemCount.get() == CartItemLimit.SELF_SALAD.getMaxCount()) {
            log.info("SelfSalad 카트가 꽉 찼습니다.");
            return 1;
        }
        return 0;
    }

    private Map<Long, Ingredient> getIngredientsMap( Set<Long> ingredientIds ){

        List<Ingredient> ingredients =
                ingredientRepository.findByIdIn(ingredientIds)
                        .orElseThrow(() -> new RuntimeException("존재하지 않는 ingredients 입니다. : "+ ingredientIds));

        log.info("Ingredients "+ingredientIds+" 번의 재료들이 존재합니다.");

        Map<Long, Ingredient> ingredientMap = new HashMap<>();

        for (Ingredient ingredient : ingredients) {
            ingredientMap.put(ingredient.getId(), ingredient);
        }
        return ingredientMap;

    }
    @Override
    public void selfSaladCartRegister(Long memberId, SelfSaladCartRegisterForm reqForm) {

        Member member = MemberUtils.getMemberById(memberRepository,memberId);

        Set<Long> ingredientIds = new HashSet<>();
        for (SelfSaladRequest ingredient : reqForm.getSelfSaladRequestList()) {

            ingredientIds.add(ingredient.getIngredientId());
        }
        Map<Long, Ingredient> ingredientMap = requireNonNull(getIngredientsMap(ingredientIds));

        Cart myCart = createOrFindCart(member);

        SelfSalad mySalad = createSelfSalad(reqForm);

        addSelfSaladIngredient(mySalad,reqForm.getSelfSaladRequestList(), ingredientMap );

        addSelfSaladItem(myCart, mySalad, reqForm);
        log.info(member.getNickname() + " 님의 장바구니에 셀프 샐러드 상품을 추가하였습니다.");

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

    private  List<SelfSaladIngredient> findSelectedIngredient(Long itemId){
        CartItem cartItem = getCartItemById(itemId);

        SelfSaladItem selfSaladItem = (SelfSaladItem) cartItem;
        Long selfSaladId = selfSaladItem.getSelfSalad().getId();

        return selfSaladIngredientRepository.findBySelfSalad_id(selfSaladId);
    }
    private Set<Long> findSelectedIngredientIds(List<SelfSaladIngredient> selectedIngredients){
        Set<Long> ingredientIds = new HashSet<>();
        for(SelfSaladIngredient myIngredients : selectedIngredients ){
            ingredientIds.add(myIngredients.getIngredient().getId());
        }
        return ingredientIds;
    }
    @Override
    @Transactional
    public List<SelectedIngredientsResponse> getSelfSaladIngredient(Long itemId){

        List<SelfSaladIngredient> selectedIngredients = findSelectedIngredient(itemId);
        Set<Long> ingredientIds = findSelectedIngredientIds(selectedIngredients);

        List<Ingredient> ingredients = ingredientRepository.findByIdIn(ingredientIds)
                .orElseThrow(() -> new IllegalArgumentException("해당 Ingredient 아이디들은 없습니다. : "+ingredientIds));
        List<SelectedIngredientsResponse> response =
         ingredients.stream()
                .flatMap(ingredient -> ingredient.getIngredientAmounts().stream()
                        .filter(amount -> ingredientIds.contains(ingredient.getId()))
                        .map(amount -> {
                            Long selectedAmount = selectedIngredients.stream()
                                    .filter(selected -> selected.getIngredient().getId().equals(ingredient.getId()))
                                    .findFirst()
                                    .map(SelfSaladIngredient::getSelectedAmount)
                                    .orElse(0L);
                            return new SelectedIngredientsResponse(
                                    ingredient.getId(),
                                    ingredient.getName(),
                                    ingredient.getPrice(),
                                    amount.getAmount().getAmountType().toString(),
                                    amount.getMax(),
                                    amount.getUnit(),
                                    amount.getCalorie(),
                                    selectedAmount
                            );
                        }))
                .collect(Collectors.toList());
        log.info(response.get(0).getName());
        return response;
    }
    @Override
    @Transactional
    public void modifySelfSaladItem(Long itemId, SelfSaladModifyForm form) {
        // selfSalad item 확인
        CartItem cartItem = getCartItemById(itemId);

        // Self Salad 찾기
        if (cartItem instanceof SelfSaladItem) {
            SelfSaladItem selfSaladItem = (SelfSaladItem)cartItem;
            SelfSalad mySalad = selfSaladItem.getSelfSalad();

            mySalad.setTotal(form.getTotalPrice(), form.getTotalCalorie());
            selfSaladRepository.save(mySalad);

            modifySelfSaladIngredient(mySalad, form.getSelfSaladModifyRequestList());
        }
    }

    private void modifySelfSaladIngredient(SelfSalad mySalad, List<SelfSaladModifyRequest> reqItems){
        // 수정 전 재료들 [재료 id : 샐러드_재료]
        Map<Long, SelfSaladIngredient> prevIngredients =
                selfSaladIngredientRepository.findBySelfSalad_id(mySalad.getId()).stream()
                        .collect(Collectors.toMap(
                                selfSaladIngredient -> selfSaladIngredient.getIngredient().getId(),
                                selfSaladIngredient -> selfSaladIngredient
                        ));
        // 수정 요청 [재료 id : 샐려드_재료 요청]
        Map<Long, SelfSaladModifyRequest> reqIngredients = reqItems.stream()
                .collect(Collectors.toMap(SelfSaladModifyRequest::getIngredientId,
                                          Function.identity()));
        // 1. 공통된 Ingredient ID 만 포함하는 Set (수량 수정)
        Set<Long> commonIds = reqItems.stream()
                .map(SelfSaladModifyRequest::getIngredientId)
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
                                          Map<Long, SelfSaladModifyRequest> reqIngredients,
                                          Set<Long> commonIds){
        log.info("수량 변경 요청 온 샐러드 재료 IDs : "+commonIds);
        List<SelfSaladIngredient> modifies = new ArrayList<>();
        for (Long commonId : commonIds) {
            SelfSaladIngredient prevIngredient = prevIngredients.get(commonId);

            SelfSaladModifyRequest reqIngredient = reqIngredients.get(commonId);
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
