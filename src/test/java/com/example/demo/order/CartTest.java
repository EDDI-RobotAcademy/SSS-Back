//package com.example.demo.order;
//
//import com.example.demo.domain.member.entity.Member;
//import com.example.demo.domain.member.repository.MemberRepository;
//import com.example.demo.domain.order.controller.form.SelfSaladCartRegisterForm;
//import com.example.demo.domain.order.controller.form.SelfSaladModifyForm;
//import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
//import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
//import com.example.demo.domain.order.controller.request.CartRegisterRequest;
//import com.example.demo.domain.order.controller.response.CartItemListResponse;
//import com.example.demo.domain.order.controller.response.SelfSaladReadResponse;
//import com.example.demo.domain.order.entity.ProductCart;
//import com.example.demo.domain.order.entity.SelfSaladCart;
//import com.example.demo.domain.order.entity.items.*;
//import com.example.demo.domain.order.repository.*;
//import com.example.demo.domain.order.service.CartService;
//import com.example.demo.domain.order.service.request.SelfSaladRequest;
//import com.example.demo.domain.products.entity.Product;
//import com.example.demo.domain.products.repository.ProductsRepository;
//import com.example.demo.domain.selfSalad.entity.*;
//import com.example.demo.domain.selfSalad.repository.AmountRepository;
//import com.example.demo.domain.selfSalad.repository.IngredientRepository;
//import com.example.demo.domain.selfSalad.repository.SelfSaladIngredientRepository;
//import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
//import com.example.demo.domain.sideProducts.entity.SideProduct;
//import com.example.demo.domain.sideProducts.entity.SideProductImg;
//import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.*;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import static java.util.Objects.requireNonNull;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class CartTest {
//    @Autowired
//    private ProductCartRepository productCartRepository;
//    @Autowired
//    private SideProductCartRepository sideProductCartRepository;
//    @Autowired
//    private SelfSaladCartRepository selfSaladCartRepository;
//
//    @Autowired
//    private ProductItemRepository productItemRepository;
//    @Autowired
//    private SideProductItemRepository sideProductItemRepository;
//    @Autowired
//    private SelfSaladItemRepository selfSaladItemRepository;
//
//    @Autowired
//    private ProductsRepository productsRepository;
//    @Autowired
//    private SideProductsRepository sideProductsRepository;
//    @Autowired
//    private IngredientRepository ingredientRepository;
//    @Autowired
//    private AmountRepository amountRepository;
//    @Autowired
//    private SelfSaladIngredientRepository selfSaladIngredientRepository;
//    @Autowired
//    private SelfSaladRepository selfSaladRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private CartService service;
//
//
////    @Mock
////    private ProductCartRepository mockProductCartRepository;
////    @Mock
////    private SideProductCartRepository mockSideProductCartRepository;
////
////    @Mock
////    private ProductItemRepository mockProductItemRepository;
////    @Mock
////    private SideProductItemRepository mockSideProductItemRepository;
////
////    @Mock
////    private ProductsRepository mockProductRepository;
////    @Mock
////    private SideProductsRepository mockSideProductRepository;
////
////    @Mock
////    private MemberRepository mockMemberRepository;
//
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//    @Test
//    private Member 멤버_아이디_확인(Long memberId){
//        Optional<Member> maybeMember =
//                memberRepository.findByMemberId(memberId);
//
//        Member member = null;
//        if(maybeMember.isPresent()) {
//            return member = maybeMember.get();
//        }
//
//        System.out.println("존재하지 않은 회원입니다.");
//        return null;
//    }
//
//    @Test
//    private Product 상품_아이디_확인(Long productId ){
//
//        // product 찾기
//        Optional<Product> maybeProduct =
//                productsRepository.findById(productId);
//        if(maybeProduct.isPresent()){
//            System.out.println("Product "+maybeProduct.get().getProductId()+" 번의 상품이 존재합니다.");
//
//            return maybeProduct.get();
//        }
//
//        System.out.println("해당 Product 상품이 없습니다.");
//        return null;
//    }
//
//    @Test
//    public void 상품_카테고리_분류(){
//
////        Product testProduct =
////                new Product("단호박 샐러드", 6000L, "상상도 못하게 비싸군요!");
////        productsRepository.save(testProduct);
//        Product testProduct =
//                new Product("고구마 샐러드", 4000L, "정말 싸군요!");
//        productsRepository.save(testProduct);
//        SideProductImg imgs = new SideProductImg( );
//        SideProduct testSideProduct =
//                new SideProduct(1L, "고구마 주스", "정말 비싸군요!", 2000L, imgs);
//        productsRepository.save(testProduct);
//
//        CartRegisterRequest item = new CartRegisterRequest(
//                ItemCategoryType.PRODUCT,3L, 3, 1L
//        );
//
//        Member member = requireNonNull(멤버_아이디_확인(item.getMemberId()));
//        Product requestProduct = requireNonNull(상품_아이디_확인(item.getItemId()));
//
//        // 회원이 보낸 장바구니 상품을 분류
//        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
//            // 카트가 존재하는지 확인
//            Optional<ProductCart> memberCart =
//                    productCartRepository.findByMember_memberId(member.getMemberId());
//
//            if(memberCart.isEmpty()){
//                새료운_카트생성(member, item, requestProduct);
//            }else{
//                System.out.println(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
//                기존카트_아이템_추가(memberCart.get(), item, requestProduct);
//            }
//
//        }
//
//    }
//    @Test
//    private void 새료운_카트생성(Member member, CartRegisterRequest item, Product requestProduct ){
//
//        ProductCart firstCart = ProductCart.builder().member(member).build();
//
//        productCartRepository.save(firstCart);
//        System.out.println(member.getNickname()+" 님의 product 카트를 생성하였습니다.");
//
//        ProductItem newProductItem = item.toProductItem(requestProduct, firstCart);
//        productItemRepository.save(newProductItem);
//
//        System.out.println(member.getNickname()+" 님의 product 카트에 첫 상품을 추가하였습니다.");
//    }
//
//    @Test
//    private void 기존카트_아이템_추가(ProductCart memberCart, CartRegisterRequest item, Product requestProduct) {
//
//        // 회원의 카트에서 상품의 아이디와 일치하는 product item 찾기
//        Optional<ProductItem> maybeProductItem =
//                productItemRepository.findByProduct_productIdAndProductCart_Id(item.getItemId(),memberCart.getId());
//
//        // product item 있다 = 카트에 해당 item 있다. = 수량 추가
//        if (maybeProductItem.isPresent()) {
//            ProductItem productItem = maybeProductItem.get();
//
//            productItem.setQuantity(productItem.getQuantity() + item.getQuantity());
//            productItemRepository.save(productItem);
//            System.out.println(requestProduct.getTitle() + " 상품의 수량을 카트에 추가하였습니다.");
//
//        } else {
//        // product item 없다 = 카트에 없는 새로운 상품 = 카트에 담기
//            ProductItem newProductItem = item.toProductItem(requestProduct, memberCart);
//
//            productItemRepository.save(newProductItem);
//            System.out.println(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
//        }
//    }
//
//    @Test
//    @Transactional
//    public void 카트아이템_리스트(){
//        Long memberId = 1L;
//        List<ProductItem> productItems = productItemRepository.findByProductCart_Member_memberId(memberId);
//        List<SideProductItem> sideProductItems = sideProductItemRepository.findBySideProductCart_Member_memberId(memberId);
//        List<SelfSaladItem> selfSaladItems = selfSaladItemRepository.findBySelfSaladCart_Member_memberId(memberId);
//
//        List<CartItemListResponse> cartItems = new ArrayList<>();
//        if(!productItems.isEmpty()){
//            for (ProductItem productItem : productItems) {
//                cartItems.add(new CartItemListResponse(productItem));
//            }
//        }
//        if(!sideProductItems.isEmpty()){
//            for (SideProductItem sideProductItem : sideProductItems) {
//                cartItems.add(new CartItemListResponse(sideProductItem));
//            }
//        }
//        if(!selfSaladItems.isEmpty()){
//            for (SelfSaladItem selfSaladItem : selfSaladItems) {
//                cartItems.add(new CartItemListResponse(selfSaladItem));
//            }
//        }
//        if(!cartItems.isEmpty()){
//            // 2. Comparator 구현하여 Stream의 sorted() 메서드에 전달
//            Comparator<CartItemListResponse> byDate = Comparator.comparing(CartItemListResponse::getAddedDate);
//            // 3. Stream으로 변환 후 정렬
//            List<CartItemListResponse> sortedItems = cartItems.stream()
//                    .sorted(byDate)
//                    .collect(Collectors.toList());
//            System.out.println("CartItem List 출력 : "+sortedItems);
//        }
//    }
//
//    @Test
//    void modifyCartItemQuantity(){
//        CartItemQuantityModifyRequest itemRequest =
//                new CartItemQuantityModifyRequest(2L, -1, ItemCategoryType.PRODUCT);
//
//        if (itemRequest.getItemCategoryType() == ItemCategoryType.PRODUCT) {
//            ProductItem productItem =
//                    productItemRepository.findById(itemRequest.getItemId()).get();
//
//            productItem.setQuantity(productItem.getQuantity() + itemRequest.getQuantity());
//            productItemRepository.save(productItem);
//            System.out.println((productItem.getId()+" 번의 product Item 의 수량이 변경되었습니다."));
//        }
//    }
//
//    @Test
//    void 카트아이템_삭제(){
//        CartItemDeleteRequest itemDelete =
//                new CartItemDeleteRequest(2L, ItemCategoryType.SELF_SALAD);
//        if (itemDelete.getItemCategoryType() == ItemCategoryType.PRODUCT) {
//
//            productItemRepository.deleteById(itemDelete.getItemId());
//            System.out.println((itemDelete.getItemId()+" 번 product Item 이 삭제되었습니다."));
//
//        } else if (itemDelete.getItemCategoryType() == ItemCategoryType.SIDE) {
//
//            sideProductItemRepository.deleteById(itemDelete.getItemId());
//            System.out.println((itemDelete.getItemId()+" 번 SideProduct Item 이 삭제되었습니다."));
//
//        }else if (itemDelete.getItemCategoryType() == ItemCategoryType.SELF_SALAD) {
//
//            SelfSalad deleteSalad =
//                    selfSaladItemRepository.findById(itemDelete.getItemId()).get().getSelfSalad();
//            selfSaladItemRepository.deleteById(itemDelete.getItemId());
//
//            System.out.println(itemDelete.getItemId()+" 번 SelfSalad Item 이 삭제되었습니다.");
//
//            selfSaladRepository.deleteById(deleteSalad.getId());
//            System.out.println(itemDelete.getItemId()+" 번 SelfSalad 가 삭제되었습니다.");
//        }
//    }
//
//    @Test
//    void 셀프샐러드_카트_제한(){
//
//        //Member member = requireNonNull(멤버_아이디_확인(1L));
//        Optional<SelfSaladCart> mySelfSaladCart =
//                selfSaladCartRepository.findByMember_memberId(1L);
//
//        if(mySelfSaladCart.isPresent()){
//            Integer selfSaladItemCount =
//                    selfSaladItemRepository.countBySelfSaladCart_id(mySelfSaladCart.get().getId());
//
//            if(selfSaladItemCount == CartItemLimit.SELF_SALAD.getMaxCount()){
//                System.out.println(("SelfSalad 카트가 꽉 찼습니다."));;
//            }else{
//                System.out.println(("SelfSalad 카트 공간이 남았습니다."));;
//            }
//        }
//    }
//
//    @Test
//    void 셀프샐러드_카트등록(){
//
//        List<SelfSaladRequest> saladRequest = new ArrayList<>();
//            saladRequest.add(new SelfSaladRequest(1L, 1000L, AmountType.GRAM));
//            saladRequest.add(new SelfSaladRequest(3L, 1000L, AmountType.GRAM));
//            saladRequest.add(new SelfSaladRequest(4L, 200L, AmountType.COUNT));
//
//        SelfSaladCartRegisterForm reqForm = new SelfSaladCartRegisterForm(
//              "진진샐러드", 2, 10000L, 360L, 1L,saladRequest
//        );
//        Member member = requireNonNull(멤버_아이디_확인(reqForm.getMemberId()));
//        Set<Long> ingredientIds = new HashSet<>();
//        for(SelfSaladRequest ingredient : saladRequest){
//
//            ingredientIds.add(ingredient.getIngredientId());
//        }
//        Map<Long, Ingredient> ingredientMap = requireNonNull(재료아이디_확인(ingredientIds));
//
//        Optional<SelfSaladCart> mySelfSaladCart =
//                selfSaladCartRepository.findByMember_memberId(member.getMemberId());
//
//        if(mySelfSaladCart.isEmpty()){
//            SelfSaladCart firstCart = 셀프샐러드_카트생성(member);
//            셀프샐러드_아이템_추가(firstCart, reqForm, ingredientMap);
//        }else{
//            System.out.println((member.getNickname()+" 님의 SelfSalad 카트는 이미 생성되어 있습니다."));
//
//            셀프샐러드_아이템_추가(mySelfSaladCart.get(), reqForm, ingredientMap);
//            System.out.println((member.getNickname()+" 님의 SelfSalad 카트에 상품을 추가하였습니다."));
//        }
//    }
//    @Test
//    private Map<Long, Ingredient> 재료아이디_확인(Set<Long> ingredientIds){
//
//        Optional <List<Ingredient>> maybeIngredients =
//                ingredientRepository.findByIdIn(ingredientIds);
//
//        if(maybeIngredients.isPresent()){
//            System.out.println(("Ingredients "+ingredientIds+" 번의 재료들이 존재합니다."));
//
//            Map<Long, Ingredient> ingredientMap = new HashMap<>();
//
//            for (Ingredient ingredient : maybeIngredients.get()) {
//                ingredientMap.put(ingredient.getId(), ingredient);
//            }
//            return ingredientMap;
//        }
//        return null;
//    }
//
//    @Test
//    private SelfSaladCart 셀프샐러드_카트생성(Member member){
//        SelfSaladCart firstCart = SelfSaladCart.builder()
//                .member(member)
//                .build();
//        selfSaladCartRepository.save(firstCart);
//        System.out.println((member.getNickname()+" 님의 SelfSalad 카트를 생성하였습니다."));
//        return firstCart;
//    }
//
//    @Test
//    private void 셀프샐러드_아이템_추가(SelfSaladCart myCart, SelfSaladCartRegisterForm reqForm,
//                                  Map<Long, Ingredient> ingredientMap){
//        // SelfSalad 저장
//        SelfSalad selfSalad = reqForm.toSelfSalad();
//        System.out.println("샐러드 출력 가즈아!"+ selfSalad);
//        selfSaladRepository.save(selfSalad);
//
//        셀프샐러드_재료_추가(selfSalad, reqForm.getSelfSaladRequestList(), ingredientMap);
//        // SelfSalad Item
//        SelfSaladItem newSelfSaladitem = reqForm.toSelfSaladItem(myCart, selfSalad);
//        selfSaladItemRepository.save(newSelfSaladitem);
//    }
//    @Test
//    private void 셀프샐러드_재료_추가(SelfSalad selfSalad, List<SelfSaladRequest> requestList,
//                                    Map<Long, Ingredient> ingredientMap){
//        // SelfSaladIngredient 저장
//        List<SelfSaladIngredient> saladIngredients = new ArrayList<>();
//        //List<Amount> amounts = new ArrayList<>();
//
//        for (SelfSaladRequest request : requestList) {
//            Amount amount =
//                    amountRepository.findByAmountType(request.getAmountType()).get();
//            Ingredient ingredient =
//                    ingredientMap.get(request.getIngredientId());
//
//            saladIngredients.add( request.toSelfSaladIngredient( selfSalad,ingredient, amount) );
//        }
//        selfSaladIngredientRepository.saveAll(saladIngredients);
//    }
//
//    @Test
//    public void 셀프샐러드_재료_읽기(){
//        Long itemId = 3L;
//        // 장바구니 수정 요청시 보낼 샐러드_재료 데이터
//        Optional<SelfSaladItem> maybeItem = selfSaladItemRepository.findById(itemId);
//        Long selfSaladId;
//        if(maybeItem.isPresent()){
//            // Self Salad 찾기
//            selfSaladId = maybeItem.get().getSelfSalad().getId();
//            List<SelfSaladIngredient> selfSaladIngredients =
//                    selfSaladIngredientRepository.findBySelfSalad_id(selfSaladId);
//            List<SelfSaladReadResponse> responseList = new ArrayList<>();
//            for(SelfSaladIngredient ingredient : selfSaladIngredients){
//                responseList.add(
//                        new SelfSaladReadResponse(ingredient.getIngredient().getId(),
//                                ingredient.getSelectedAmount()));
//            }
//            System.out.println("수정할 샐러드 재료 정보 보내기 :"+responseList);
//        }
//    }
//    @Test
//    @Transactional
//    @Commit
//    public void 셀프샐러드_카트아이템_수정(){
//        Long itemId = 3L;
//        List<SelfSaladRequest> requestItems = Arrays.asList(
//                new SelfSaladRequest(2L, 22L, AmountType.GRAM),
//                new SelfSaladRequest(1L, 100L, AmountType.GRAM)
//                //,new SelfSaladRequest(3L, 33L, AmountType.GRAM)
//        );
//        SelfSaladModifyForm modifyForm = new SelfSaladModifyForm(2222L,1L, requestItems);
//
//        // selfSalad item 확인
//        Optional<SelfSaladItem> maybeItem = selfSaladItemRepository.findById(itemId);
//
//        if(maybeItem.isPresent()){
//            // Self Salad 찾기
//            SelfSalad mySalad = maybeItem.get().getSelfSalad();
//            셀프샐러드_수정(mySalad, modifyForm.getTotalPrice(), modifyForm.getTotalCalorie());
//
//            셀프샐러드_재료_수정(mySalad, modifyForm.getSelfSaladRequestList());
//        }
//    }
//    @Test
//    @Commit
//    private void 셀프샐러드_수정(SelfSalad mySalad, Long price, Long calorie){
//        mySalad.setTotal(price, calorie);
//        selfSaladRepository.save(mySalad);
//    }
//    @Test
//    @Commit
//    private void 셀프샐러드_재료_수정(SelfSalad mySalad, List<SelfSaladRequest> requestItems){
//        // 수정 전 재료들 [재료 id : 샐러드_재료]
//        Map<Long, SelfSaladIngredient> prevIngredients =
//                selfSaladIngredientRepository.findBySelfSalad_id(mySalad.getId()).stream()
//                        .collect(Collectors.toMap(
//                                selfSaladIngredient -> selfSaladIngredient.getIngredient().getId(),
//                                selfSaladIngredient -> selfSaladIngredient
//                        ));
//        // 수정 요청 [재료 id : 샐려드_재료 요청]
//        Map<Long, SelfSaladRequest> reqIngredients = requestItems.stream()
//                .collect(Collectors.toMap(SelfSaladRequest::getIngredientId,
//                                          Function.identity()));
//        // 1. 공통된 Ingredient ID 만 포함하는 Set (수량 수정)
//        Set<Long> commonIds = requestItems.stream()
//                .map(SelfSaladRequest::getIngredientId)
//                .collect(Collectors.toSet());
//        commonIds.retainAll(prevIngredients.keySet());
//
//        // 2. 새롭게 추가된 Ingredient ID 만 포함하는 Set - HashSet 은 중복된 값이 없는 집합을 저장
//        Set<Long> newIngredientIds = new HashSet<>(reqIngredients.keySet());
//        newIngredientIds.removeAll(commonIds);
//
//        // 3. 삭제해야 하는 Ingredient ID 만 포함하는 Set (수정 후 요청객체에 없는 수정전 샐러드_재료)
//        Set<Long> deleteIngredientIds = prevIngredients.values().stream()
//                .filter(ingredient -> !commonIds.contains(ingredient.getIngredient().getId()))
//                .map(ingredient -> ingredient.getIngredient().getId())
//                .collect(Collectors.toSet());
//
//        // 1. 수량 수정
//        if( ! commonIds.isEmpty()){
//            샐러드재료_선택수량_샐러드수정(prevIngredients, reqIngredients, commonIds);
//        }
//        // 2. 새로운 재료 추가
//        if( ! newIngredientIds.isEmpty()){
//            샐러드_재료추가_샐러드수정(requestItems, newIngredientIds, mySalad);
//        }
//        // 3. 샐러드_재료 삭제
//        if( ! deleteIngredientIds.isEmpty()){
//            삭제될_샐러드재료_샐러드수정(mySalad.getId(), deleteIngredientIds);
//        }
//    }
//    @Test
//    @Commit
//    private void 샐러드재료_선택수량_샐러드수정 (Map<Long, SelfSaladIngredient> prevIngredients,
//                                               Map<Long, SelfSaladRequest> reqIngredients,
//                                               Set<Long> commonIds){
//        System.out.println("수량 변경 요청 온 샐러드 재료 IDs : "+commonIds);
//        List<SelfSaladIngredient> modifies = new ArrayList<>();
//        for (Long commonId : commonIds) {
//            SelfSaladIngredient prevIngredient = prevIngredients.get(commonId);
//
//            SelfSaladRequest reqIngredient = reqIngredients.get(commonId);
//            if( prevIngredient.getSelectedAmount() != reqIngredient.getSelectedAmount()){
//                prevIngredient.setSelectedAmount(reqIngredient.getSelectedAmount());
//                modifies.add(prevIngredient);
//            }
//        }
//        selfSaladIngredientRepository.saveAll(modifies);
//    }
//
//    @Test
//    @Commit
//    private void 샐러드_재료추가_샐러드수정(List<SelfSaladRequest> requestItems,
//                                              Set<Long> newIngredientIds,
//                                              SelfSalad prevSalad){
//        System.out.println("새롭게 추가할 샐러드 재료 IDs : "+newIngredientIds);
//        List<SelfSaladRequest> addIngredients = requestItems.stream()
//                .filter(item -> newIngredientIds.contains(item.getIngredientId()))
//                .collect(Collectors.toList());
//
//        Map<Long, Ingredient> ingredientMap = 재료아이디_확인(newIngredientIds);
//
//        셀프샐러드_재료_추가(prevSalad, addIngredients, ingredientMap);
//    }
//
//    @Test
//    @Commit
//    private void 삭제될_샐러드재료_샐러드수정(Long saladId, Set<Long> deleteIngredientIds){
//
//        System.out.println("삭제할 샐러드_재료 IDs: "+deleteIngredientIds);
//        selfSaladIngredientRepository.deleteAllBySelfSalad_IdAndIngredient_IdIn
//                (saladId, deleteIngredientIds);
//    }
//
//}
