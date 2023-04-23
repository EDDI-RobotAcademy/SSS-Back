package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.AddressRepository;
import com.example.demo.domain.member.service.MemberServiceImpl;
import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;
import com.example.demo.domain.order.entity.*;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import com.example.demo.domain.order.repository.*;
import com.example.demo.domain.order.service.request.DeliveryRegisterRequest;
import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import com.example.demo.domain.order.service.request.PaymentRequest;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.*;
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
    final private OrderItemRepository orderItemRepository;
    final private OrderStateRepository orderStateRepository;
    final private OrderInfoStateRepository orderInfoStateRepository;

    final private PaymentRepository paymentRepository;

    final private MemberServiceImpl memberService;



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

    private OrderInfo registerOrderInfo(Member member, Long totalOrderPrice){

        OrderInfo newOrderInfo = new OrderInfo(totalOrderPrice, member);
        orderInfoRepository.save(newOrderInfo);
        log.info(member.getNickname() + " 님의 주문 테이블이 생성되었습니다.");

        return newOrderInfo;
    }

    private OrderInfoState registerOrderState(OrderInfo myOrderInfo){

        final OrderState orderState =
                orderStateRepository.findByOrderStateType(OrderStateType.PAYMENT_COMPLETE);

        final OrderInfoState orderInfoState =
                new OrderInfoState(myOrderInfo, orderState);
        return orderInfoState;
    }

    private void registerPayment(PaymentRequest reqPayment, OrderInfo myOrderInfo){
        Payment myPayment =
                reqPayment.toPayment(myOrderInfo);
        paymentRepository.save(myPayment);
    }
    @Override
    public void orderRegister(Long memberId, OrderInfoRegisterForm orderForm){
        // { 상품 카테고리, 상품 id, 상품 수량, 상품 가격 } 주문 list
        try{
            Member member = requireNonNull(memberService.checkMember(memberId));

            // myOrderInfo 생성
            OrderInfo myOrderInfo = registerOrderInfo(member, orderForm.getTotalOrderPrice());

            // orderInfoState 저장
            registerOrderState(myOrderInfo);

            // payment 저장
            registerPayment(orderForm.getPaymentRequest(), myOrderInfo);

            // orderItem 분류 및 저장
            addOrderItemByCategory(orderForm.getOrderItemRegisterRequestList(), myOrderInfo);

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
        }
    }

    private void addOrderItemByCategory(List<OrderItemRegisterRequest> orderItems, OrderInfo myOrderInfo){
        List<OrderItemRegisterRequest> productOrderItems = new ArrayList<>();
        List<OrderItemRegisterRequest> sideProductOrderItems = new ArrayList<>();
        List<OrderItemRegisterRequest> selfSaladOrderItems = new ArrayList<>();

        for(OrderItemRegisterRequest item : orderItems){
            switch (item.getItemCategoryType()) {
                case PRODUCT:
                    productOrderItems.add(item); break;
                case SIDE:
                    sideProductOrderItems.add(item); break;
                case SELF:
                    selfSaladOrderItems.add(item); break;
                default:
                    throw new IllegalArgumentException("존재하지 않는 상품 카테고리 입니다. : " + item.getItemCategoryType());
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
        orderItemRepository.saveAll(productOrderItemList);
        log.info(productOrderItemList + " 상품을 주문상품에 추가하였습니다.");
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
        orderItemRepository.saveAll(sideProductOrderItems);
        log.info(sideProductOrderItems + " 상품을 주문상품에 추가하였습니다.");
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
        orderItemRepository.saveAll(selfSaladOrderItems);
        log.info(selfSaladOrderItems + " 상품을 주문상품에 추가하였습니다.");
    }

}
