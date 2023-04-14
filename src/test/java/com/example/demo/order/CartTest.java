package com.example.demo.order;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.request.CartItemDeleteRequest;
import com.example.demo.domain.order.controller.request.CartItemQuantityModifyRequest;
import com.example.demo.domain.order.controller.request.CartRegisterRequest;
import com.example.demo.domain.order.controller.request.SelfSaladCartRegisterForm;
import com.example.demo.domain.order.controller.response.CartItemListResponse;
import com.example.demo.domain.order.entity.ProductCart;
import com.example.demo.domain.order.entity.SelfSaladCart;
import com.example.demo.domain.order.entity.items.*;
import com.example.demo.domain.order.repository.*;
import com.example.demo.domain.order.service.request.SelfSaladRequest;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.*;
import com.example.demo.domain.selfSalad.repository.AmountRepository;
import com.example.demo.domain.selfSalad.repository.IngredientRepository;
import com.example.demo.domain.selfSalad.repository.SelfSaladIngredientRepository;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

@SpringBootTest
@AutoConfigureMockMvc
public class CartTest {
    @Autowired
    private ProductCartRepository productCartRepository;
    @Autowired
    private SideProductCartRepository sideProductCartRepository;
    @Autowired
    private SelfSaladCartRepository selfSaladCartRepository;

    @Autowired
    private ProductItemRepository productItemRepository;
    @Autowired
    private SideProductItemRepository sideProductItemRepository;
    @Autowired
    private SelfSaladItemRepository selfSaladItemRepository;

    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private SideProductsRepository sideProductsRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private AmountRepository amountRepository;
    @Autowired
    private SelfSaladIngredientRepository selfSaladIngredientRepository;
    @Autowired
    private SelfSaladRepository selfSaladRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Mock
    private ProductCartRepository mockProductCartRepository;
    @Mock
    private SideProductCartRepository mockSideProductCartRepository;

    @Mock
    private ProductItemRepository mockProductItemRepository;
    @Mock
    private SideProductItemRepository mockSideProductItemRepository;

    @Mock
    private ProductsRepository mockProductRepository;
    @Mock
    private SideProductsRepository mockSideProductRepository;

    @Mock
    private MemberRepository mockMemberRepository;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    private Member 멤버_아이디_확인(Long memberId){
        Optional<Member> maybeMember =
                memberRepository.findByMemberId(memberId);

        Member member = null;
        if(maybeMember.isPresent()) {
            return member = maybeMember.get();
        }

        System.out.println("존재하지 않은 회원입니다.");
        return null;
    }

    @Test
    private Product 상품_아이디_확인(Long productId ){

        // product 찾기
        Optional<Product> maybeProduct =
                productsRepository.findById(productId);
        if(maybeProduct.isPresent()){
            System.out.println("Product "+maybeProduct.get().getProductId()+" 번의 상품이 존재합니다.");

            return maybeProduct.get();
        }

        System.out.println("해당 Product 상품이 없습니다.");
        return null;
    }

    @Test
    public void 상품_카테고리_분류(){

        CartRegisterRequest item = new CartRegisterRequest(
                ItemCategoryType.PRODUCT,2L, 20, 1L
        );

//        Product testProduct =
//                new Product("단호박 샐러드", 6000L, "상상도 못하게 비싸군요!");
//        productsRepository.save(testProduct);
        Product testProduct =
                new Product("고구마 샐러드", 4000L, "정말 싸군요!");
        productsRepository.save(testProduct);

        Member member = requireNonNull(멤버_아이디_확인(item.getMemberId()));
        Product requestProduct = requireNonNull(상품_아이디_확인(item.getItemId()));

        // 회원이 보낸 장바구니 상품을 분류
        if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            // 카트가 존재하는지 확인
            Optional<ProductCart> memberCart =
                    productCartRepository.findByMember_memberId(member.getMemberId());

            if(memberCart.isEmpty()){
                새료운_카트생성(member, item, requestProduct);
            }else{
                System.out.println(member.getNickname()+" 님의 product 카트는 이미 생성되어 있습니다.");
                기존카트_아이템_추가(memberCart.get(), item, requestProduct);
            }

        }

    }
    @Test
    private void 새료운_카트생성(Member member, CartRegisterRequest item, Product requestProduct ){

        ProductCart firstCart = ProductCart.builder().member(member).build();

        productCartRepository.save(firstCart);
        System.out.println(member.getNickname()+" 님의 product 카트를 생성하였습니다.");

        ProductItem newProductItem = item.toProductItem(requestProduct, firstCart);
        productItemRepository.save(newProductItem);

        System.out.println(member.getNickname()+" 님의 product 카트에 첫 상품을 추가하였습니다.");
    }

    @Test
    private void 기존카트_아이템_추가(ProductCart memberCart, CartRegisterRequest item, Product requestProduct) {

        // 회원의 카트에서 상품의 아이디와 일치하는 product item 찾기
        Optional<ProductItem> maybeProductItem =
                productItemRepository.findByProduct_productIdAndProductCart_Id(item.getItemId(),memberCart.getId());

        // product item 있다 = 카트에 해당 item 있다. = 수량 추가
        if (maybeProductItem.isPresent()) {
            ProductItem productItem = maybeProductItem.get();

            productItem.setQuantity(productItem.getQuantity() + item.getQuantity());
            productItemRepository.save(productItem);
            System.out.println(requestProduct.getTitle() + " 상품의 수량을 카트에 추가하였습니다.");

        } else {
        // product item 없다 = 카트에 없는 새로운 상품 = 카트에 담기
            ProductItem newProductItem = item.toProductItem(requestProduct, memberCart);

            productItemRepository.save(newProductItem);
            System.out.println(requestProduct.getTitle() + " 상품을 카트에 추가하였습니다.");
        }
    }

    @Test
    @Transactional
    public void 카트아이템_리스트(){
        Long memberId = 1L;

        List<ProductItem> productItems = productItemRepository.findByProductCart_Member_memberId(memberId);
        List<SideProductItem> sideProductItems = sideProductItemRepository.findBySideProductCart_Member_memberId(memberId);
        // repo 에서 items 를 찾기 > 컬렉션 객체를 스트림으로 처리 > 스트림의 각 요소를 다른 형대로 변환
        // 컬렉션 객체를 스트림으로 처리
        // 스트림의 각 요소를 다른 형태의 요소로 변환 > productItem 의 필드를 이용해 CartItemListResponse 객체 생성
        List<CartItemListResponse> cartItems = Stream.concat(
                    productItems.stream().map(productItem -> new CartItemListResponse(
                            productItem.getId(),
                            productItem.getQuantity(),
                            productItem.getAddedDate(),
                            productItem.getProduct().getProductId(),
                            productItem.getProduct().getTitle(),
                            "editedImg",
                            productItem.getProduct().getPrice())),
                    sideProductItems.stream().map(sideProductItem -> new CartItemListResponse(
                            sideProductItem.getId(),
                            sideProductItem.getQuantity(),
                            sideProductItem.getAddedDate(),
                            sideProductItem.getSideProduct().getSideProductId(),
                            sideProductItem.getSideProduct().getTitle(),
                            "editedImg",
                            sideProductItem.getSideProduct().getPrice())))
                .sorted(Comparator.comparing(CartItemListResponse::getAddedDate).reversed())
                .collect(Collectors.toList());
        System.out.println("CartItem List 출력 : "+cartItems);
    }

    @Test
    void modifyCartItemQuantity(){
        CartItemQuantityModifyRequest itemRequest =
                new CartItemQuantityModifyRequest(2L, -1, ItemCategoryType.PRODUCT);

        if (itemRequest.getItemCategoryType() == ItemCategoryType.PRODUCT) {
            ProductItem productItem =
                    productItemRepository.findById(itemRequest.getItemId()).get();

            productItem.setQuantity(productItem.getQuantity() + itemRequest.getQuantity());
            productItemRepository.save(productItem);
            System.out.println((productItem.getId()+" 번의 product Item 의 수량이 변경되었습니다."));
        }
    }

    @Test
    void deleteCartItem(){
        CartItemDeleteRequest itemDelete =
                new CartItemDeleteRequest(3L, ItemCategoryType.PRODUCT);
        if (itemDelete.getItemCategoryType() == ItemCategoryType.PRODUCT) {

            productItemRepository.deleteById(itemDelete.getItemId());
            System.out.println((itemDelete.getItemId()+" 번 product Item 이 삭제되었습니다."));

        } else if (itemDelete.getItemCategoryType() == ItemCategoryType.SIDE) {

            sideProductItemRepository.deleteById(itemDelete.getItemId());
            System.out.println((itemDelete.getItemId()+" 번 SideProduct Item 이 삭제되었습니다."));
        }
    }

    @Test
    void 셀프샐러드_카트등록(){

        List<SelfSaladRequest> saladRequest = new ArrayList<>();
            saladRequest.add(new SelfSaladRequest(1L, 1000L, AmountType.GRAM));
            //saladRequest.add(new SelfSaladRequest(3L, 200L, AmountType.GRAM));

        SelfSaladCartRegisterForm reqForm = new SelfSaladCartRegisterForm(
              "창주샐러드", 3, 10000L, 360L, 1L,saladRequest
        );
        Member member = requireNonNull(멤버_아이디_확인(reqForm.getMemberId()));
        Map<Long, Ingredient> ingredientMap = requireNonNull(재료아이디_확인(reqForm));

        Optional<SelfSaladCart> mySelfSaladCart =
                selfSaladCartRepository.findByMember_memberId(member.getMemberId());

        if(mySelfSaladCart.isEmpty()){
            SelfSaladCart firstCart = 셀프샐러드_카트생성(member);
            셀프샐러드_아이템_추가(firstCart, reqForm, ingredientMap);
        }else{
            System.out.println((member.getNickname()+" 님의 SelfSalad 카트는 이미 생성되어 있습니다."));

            셀프샐러드_아이템_추가(mySelfSaladCart.get(), reqForm, ingredientMap);
            System.out.println((member.getNickname()+" 님의 SelfSalad 카트에 상품을 추가하였습니다."));
        }
    }
    @Test
    private Map<Long, Ingredient> 재료아이디_확인(SelfSaladCartRegisterForm requestForm){

        List<Long> ingredientIds = new ArrayList<>();
        for(SelfSaladRequest ingredient : requestForm.getSelfSaladRequestList()){

            ingredientIds.add(ingredient.getIngredientId());
        }
        Optional <List<Ingredient>> maybeIngredients =
                ingredientRepository.findByIdIn(ingredientIds);

        if(maybeIngredients.isPresent()){
            System.out.println(("Ingredients "+ingredientIds+" 번의 재료들이 존재합니다."));

            Map<Long, Ingredient> ingredientMap = new HashMap<>();

            for (Ingredient ingredient : maybeIngredients.get()) {
                ingredientMap.put(ingredient.getId(), ingredient);
            }
            return ingredientMap;
        }
        return null;
    }

    @Test
    private SelfSaladCart 셀프샐러드_카트생성(Member member){
        SelfSaladCart firstCart = SelfSaladCart.builder()
                .member(member)
                .build();
        selfSaladCartRepository.save(firstCart);
        System.out.println((member.getNickname()+" 님의 SelfSalad 카트를 생성하였습니다."));
        return firstCart;
    }

    @Test
    private void 셀프샐러드_아이템_추가(SelfSaladCart myCart, SelfSaladCartRegisterForm reqForm,
                                  Map<Long, Ingredient> ingredientMap){
        // SelfSalad 저장
        SelfSalad selfSalad = reqForm.toSelfSalad();
        System.out.println("샐러드 출력 가즈아!"+ selfSalad);
        selfSaladRepository.save(selfSalad);

        // SelfSaladIngredient 저장
        List<SelfSaladIngredient> saladIngredients = new ArrayList<>();
        //List<Amount> amounts = new ArrayList<>();

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
