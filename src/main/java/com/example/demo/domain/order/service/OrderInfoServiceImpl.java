package com.example.demo.domain.order.service;

import com.example.demo.domain.cart.entity.cartItems.ItemCategoryType;
import com.example.demo.domain.cart.service.CartServiceImpl;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import com.example.demo.domain.order.repository.OrderInfoRepository;
import com.example.demo.domain.order.repository.ProductOrderItemRepository;
import com.example.demo.domain.order.repository.SelfSaladOrderItemRepository;
import com.example.demo.domain.order.repository.SideProductOrderItemRepository;
import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService {
    final private ProductsRepository productsRepository;
    final private SideProductsRepository sideProductsRepository;
    final private SelfSaladRepository selfSaladRepository;
    final private OrderInfoRepository orderInfoRepository;
    final private ProductOrderItemRepository productOrderItemRepository;
    final private SideProductOrderItemRepository sideProductOrderItemRepository;
    final private SelfSaladOrderItemRepository selfSaladOrderItemRepository;
    final private CartServiceImpl cartService;


    private Map<Long, Product> checkProducts(List<OrderItemRegisterRequest> productItems){

        Set<Long> productIds = new HashSet<>();
        for(OrderItemRegisterRequest orderItem : productItems ){

            productIds.add(orderItem.getItemId());
        }
        Optional <List<Product>> maybeProducts =
                productsRepository.findByProductIdIn(productIds);

        if(maybeProducts.isPresent()){
            log.info("Products "+productIds+" 번의 상품들이 존재합니다.");

            Map<Long, Product> productMap = new HashMap<>();

            for (Product product : maybeProducts.get()) {
                productMap.put(product.getProductId(), product);
            }
            return productMap;
        }
        return null;
    }

    private Map<Long, SideProduct> checkSideProducts(List<OrderItemRegisterRequest> sideProductItems){

        Set<Long> sideProductIds = new HashSet<>();
        for(OrderItemRegisterRequest orderItem : sideProductItems ){

            sideProductIds.add(orderItem.getItemId());
        }
        Optional <List<SideProduct>> maybeSideProducts =
                sideProductsRepository.findBySideProductIdIn(sideProductIds);

        if(maybeSideProducts.isPresent()){
            log.info("SideProducts "+sideProductIds+" 번의 상품들이 존재합니다.");

            Map<Long, SideProduct> sideProductMap = new HashMap<>();

            for (SideProduct sideProduct : maybeSideProducts.get()) {
                sideProductMap.put(sideProduct.getSideProductId(), sideProduct);
            }
            return sideProductMap;
        }
        return null;
    }

    private Map<Long, SelfSalad> checkSelfSalad(List<OrderItemRegisterRequest> selfSaladItems){

        Set<Long> selfSaladIds = new HashSet<>();
        for(OrderItemRegisterRequest orderItem : selfSaladItems ){

            selfSaladIds.add(orderItem.getItemId());
        }
        Optional <List<SelfSalad>> maybeSelfSalad =
                selfSaladRepository.findByIdIn(selfSaladIds);

        if(maybeSelfSalad.isPresent()){
            log.info("SideProducts "+selfSaladIds+" 번의 상품들이 존재합니다.");

            Map<Long, SelfSalad> selfSaladMap = new HashMap<>();

            for (SelfSalad selfSalad : maybeSelfSalad.get()) {
                selfSaladMap.put(selfSalad.getId(), selfSalad);
            }
            return selfSaladMap;
        }
        return null;
    }


    private OrderInfo createOrder(Member member, Long totalOrderPrice){
        OrderInfo newOrderInfo = new OrderInfo(totalOrderPrice, member);

        orderInfoRepository.save(newOrderInfo);
        log.info(member.getNickname() + " 님의 첫번째 주문이 생성하였습니다.");

        return newOrderInfo;
    }

    @Override
    public void classifyOrderItemCategory(Long memberId, Long totalOrderPrice, List<OrderItemRegisterRequest> orderItems){
        // { 상품 카테고리, 상품 id, 상품 수량, 상품 가격 } 주문 list
        Member member = requireNonNull(cartService.checkMember(memberId));

        OrderInfo myOrderInfo = createOrder(member, totalOrderPrice);

        List<OrderItemRegisterRequest> productOrderItems = new ArrayList<>();
        List<OrderItemRegisterRequest> sideProductOrderItems = new ArrayList<>();
        List<OrderItemRegisterRequest> selfSaladOrderItems = new ArrayList<>();

        for(OrderItemRegisterRequest item : orderItems){

            if (item.getItemCategoryType() == ItemCategoryType.PRODUCT) {
                productOrderItems.add(item);

            } else if (item.getItemCategoryType() == ItemCategoryType.SIDE) {
                sideProductOrderItems.add(item);

            } else if (item.getItemCategoryType() == ItemCategoryType.SELF_SALAD) {
                selfSaladOrderItems.add(item);
            } else{
                throw new IllegalArgumentException("존재하지 않은 상품 카테고리 입니다. : " + item.getItemCategoryType());
            }
        }

        if( ! productOrderItems.isEmpty()){
            addProductOrderItems( productOrderItems, myOrderInfo);
        }
        if( ! sideProductOrderItems.isEmpty()){
            addSideProductsOrderItems( sideProductOrderItems, myOrderInfo);
        }
        if( ! selfSaladOrderItems.isEmpty()){
            addSelfSaladOrderItems( selfSaladOrderItems, myOrderInfo);
        }
        orderInfoRepository.save(myOrderInfo);
    }

    private void addProductOrderItems(List<OrderItemRegisterRequest> productItems, OrderInfo myOrderInfo) {

        Map<Long, Product> productMap = requireNonNull(checkProducts(productItems));

        List<ProductOrderItem> productOrderItemList = new ArrayList<>();

        for(OrderItemRegisterRequest orderItem : productItems){

            Product product = productMap.get(orderItem.getItemId());

            if(Objects.equals(orderItem.getItemId(), product.getProductId())){

                productOrderItemList.add(orderItem.toProductOrderItem(product, myOrderInfo));
            }
        }
        productOrderItemRepository.saveAll(productOrderItemList);
        log.info(productOrderItemList + " 상품을 주문에 추가하였습니다.");
    }
    private void addSideProductsOrderItems(List<OrderItemRegisterRequest> sideProductItems, OrderInfo myOrderInfo) {

        Map<Long, SideProduct> sideProductMap = requireNonNull(checkSideProducts(sideProductItems));

        List<SideProductOrderItem> sideProductOrderItems = new ArrayList<>();

        for(OrderItemRegisterRequest orderItem : sideProductItems){

            SideProduct sideProduct = sideProductMap.get(orderItem.getItemId());

            if(Objects.equals(orderItem.getItemId(), sideProduct.getSideProductId())){

                sideProductOrderItems.add(orderItem.toSideProductOrderItem(sideProduct, myOrderInfo));
            }
        }
        sideProductOrderItemRepository.saveAll(sideProductOrderItems);
        log.info(sideProductOrderItems + " 상품을 주문에 추가하였습니다.");
    }

    private void addSelfSaladOrderItems(List<OrderItemRegisterRequest> selfSaladItems, OrderInfo myOrderInfo) {

        Map<Long, SelfSalad> selfSaladMap = requireNonNull(checkSelfSalad(selfSaladItems));

        List<SelfSaladOrderItem> selfSaladOrderItems = new ArrayList<>();

        for(OrderItemRegisterRequest orderItem : selfSaladItems){

            SelfSalad selfSalad = selfSaladMap.get(orderItem.getItemId());

            if(Objects.equals(orderItem.getItemId(), selfSalad.getId())){

                selfSaladOrderItems.add(orderItem.toSelfSaladOrderItem(selfSalad, myOrderInfo));
            }
        }
        selfSaladOrderItemRepository.saveAll(selfSaladOrderItems);
        log.info(selfSaladOrderItems + " 상품을 주문에 추가하였습니다.");
    }

}
