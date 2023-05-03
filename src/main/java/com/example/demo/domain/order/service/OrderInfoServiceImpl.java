package com.example.demo.domain.order.service;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.AddressRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.order.controller.form.OrderInfoRegisterForm;
import com.example.demo.domain.order.controller.response.OrderInfoListResponse;
import com.example.demo.domain.order.controller.response.OrderItemListResponse;
import com.example.demo.domain.order.entity.*;
import com.example.demo.domain.order.entity.orderItems.OrderItem;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import com.example.demo.domain.order.repository.*;
import com.example.demo.domain.order.service.request.DeliveryAddressRequest;
import com.example.demo.domain.order.service.request.DeliveryRegisterRequest;
import com.example.demo.domain.order.service.request.OrderItemRegisterRequest;
import com.example.demo.domain.order.service.request.PaymentRequest;
import com.example.demo.domain.products.entity.Product;
import com.example.demo.domain.products.repository.ProductsRepository;
import com.example.demo.domain.selfSalad.entity.SelfSalad;
import com.example.demo.domain.selfSalad.repository.SelfSaladRepository;
import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.repository.SideProductsRepository;
import com.example.demo.domain.utility.member.MemberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    final private AddressRepository addressRepository;
    final private DeliveryRepository deliveryRepository;

    final private MemberRepository memberRepository;


    private Set<Long> getItemIds(List<OrderItemRegisterRequest> orderItems){
        Set<Long> itemIds = new HashSet<>();
        for(OrderItemRegisterRequest orderItem : orderItems ){

            itemIds.add(orderItem.getItemId());
        }
        return itemIds;
    }

    private Map<Long, Product> getProductsMap(Set<Long> productIds){

        List<Product> products = productsRepository.findByProductIdIn(productIds)
                .orElseThrow(() -> new RuntimeException("등록된 Product 상품이 아닙니다. : " + productIds));

        log.info("Products "+productIds+" 번의 상품들이 존재합니다.");
        Map<Long, Product> productMap = new HashMap<>();

        for (Product product : products) {
            productMap.put(product.getProductId(), product);
        }
        return productMap;
    }

    private Map<Long, SideProduct> getSideProductsMap(Set<Long> sideProductIds){

        List<SideProduct> sideProducts = sideProductsRepository.findBySideProductIdIn(sideProductIds)
                .orElseThrow(() -> new RuntimeException("등록된 SideProduct 상품이 아닙니다. : " + sideProductIds));

        log.info("SideProducts "+sideProductIds+" 번의 상품들이 존재합니다.");
        Map<Long, SideProduct> sideProductMap = new HashMap<>();

        for (SideProduct sideProduct :sideProducts) {
            sideProductMap.put(sideProduct.getSideProductId(), sideProduct);
        }
        return sideProductMap;
    }

    private Map<Long, SelfSalad> getSelfSaladMap(Set<Long> selfSaladIds){

        List<SelfSalad> selfSalads = selfSaladRepository.findByIdIn(selfSaladIds)
                .orElseThrow(() -> new RuntimeException("등록된 SelfSalad 상품이 아닙니다. : " + selfSaladIds));

        log.info("SelfSalads "+selfSaladIds+" 번의 상품들이 존재합니다.");
        Map<Long, SelfSalad> selfSaladMap = new HashMap<>();

        for (SelfSalad selfSalad : selfSalads) {
            selfSaladMap.put(selfSalad.getId(), selfSalad);
        }
        return selfSaladMap;
    }

    private OrderInfo registerOrderInfo(Member member, Long totalOrderPrice){

        OrderInfo newOrderInfo = new OrderInfo(totalOrderPrice, member);
        orderInfoRepository.save(newOrderInfo);
        log.info(member.getNickname() + " 님의 주문 테이블이 생성되었습니다.");

        return newOrderInfo;
    }

    private void registerOrderState(OrderInfo myOrderInfo){

        final OrderState orderState =
                orderStateRepository.findByOrderStateType(OrderStateType.PAYMENT_COMPLETE);

        orderInfoStateRepository.save(
                new OrderInfoState(myOrderInfo, orderState));
    }

    private void registerPayment(PaymentRequest reqPayment, OrderInfo myOrderInfo){

        Payment myPayment = reqPayment.toPayment(myOrderInfo);
        paymentRepository.save(myPayment);
    }

    @Override
    public Long registerNewAddress(Long memberId, DeliveryAddressRequest addressRequest){
        Member member = MemberUtils.getMemberById(memberRepository,memberId);
        Address newAddress = addressRequest.toAddress(member);

        addressRepository.save(newAddress);
        return newAddress.getId();
    }

    private Address getDeliveryAddress(DeliveryRegisterRequest reqDelivery){
        if(reqDelivery.getAddressId() != null){

            return addressRepository.findById(reqDelivery.getAddressId())
                    .orElseThrow(() -> new RuntimeException("등록된 주소가 아닙니다. : " + reqDelivery.getAddressId()));
        }
        throw new RuntimeException("배송지 주소가 지정되지 않았습니다.");
    }

    private void registerDelivery(DeliveryRegisterRequest reqDelivery, OrderInfo myOrderInfo,
                                  Address myAddress){
        Delivery myDelivery =
                reqDelivery.toDelivery(myAddress, myOrderInfo);
        deliveryRepository.save(myDelivery);
        log.info("Delivery 등록이 완료되었습니다. :"+myDelivery.getDeliveryId());
    }

    @Override
    public void orderRegister(Long memberId, OrderInfoRegisterForm orderForm){
        // { 상품 카테고리, 상품 id, 상품 수량, 상품 가격 } 주문 list
        Member member = MemberUtils.getMemberById(memberRepository,memberId);

        // myOrderInfo 생성
        OrderInfo myOrderInfo = registerOrderInfo(member, orderForm.getTotalOrderPrice());

        // orderInfoState 저장
        registerOrderState(myOrderInfo);

        // payment 저장
        registerPayment(orderForm.getPaymentRequest(), myOrderInfo);

        // orderItem 분류 및 저장
        addOrderItemByCategory(orderForm.getOrderItemRegisterRequestList(), myOrderInfo);

        // 등록했던 배송지 address 반환
        Address myAddress = getDeliveryAddress(orderForm.getDeliveryRegisterRequest());

        // delivery 저장
        registerDelivery(orderForm.getDeliveryRegisterRequest(), myOrderInfo, myAddress);
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
        Set<Long> productIds = getItemIds(productItems);
        Map<Long, Product> productMap = getProductsMap(productIds);

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
        Set<Long> sideProductIds = getItemIds(sideProductItems);
        Map<Long, SideProduct> sideProductMap = getSideProductsMap(sideProductIds);

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
        Set<Long> selfSaladIds = getItemIds(selfSaladItems);
        Map<Long, SelfSalad> selfSaladMap = getSelfSaladMap(selfSaladIds);

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

    @Override
    public Boolean updateOrderState(Long orderId, OrderStateType orderStateType){
        Optional<OrderInfo> myOrderInfo = orderInfoRepository.findById(orderId);
        if(myOrderInfo.isPresent()){

            final OrderState updateState =
                    orderStateRepository.findByOrderStateType(orderStateType);

            final OrderInfoState orderInfoState =
                    orderInfoStateRepository.findByOrderInfo_id(orderId);

            orderInfoState.setOrderState(myOrderInfo.get(), updateState);
            orderInfoStateRepository.save(orderInfoState);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public List<OrderInfoListResponse> orderInfoListResponse(Long memberId){

        List<OrderInfo> orderInfoList = orderInfoRepository.findByMember_memberId(memberId);
        List<OrderInfoListResponse> responseList = new ArrayList<>();
        for(OrderInfo orderInfo : orderInfoList){
            responseList.add(
                    new OrderInfoListResponse(
                            orderInfo,
                            orderInfo.getPayment(),
                            orderInfo.getDelivery(),
                            orderInfo.getDelivery().getAddress(),
                            getOrderStateType(orderInfo.getOrderInfoState()),
                            getOrderItems(orderInfo.getOrderItems())
                    )
            );
        }
        return responseList;
    }

    private OrderStateType getOrderStateType(Set<OrderInfoState> orderInfoStates){
        OrderStateType orderStateType = null;
        for(OrderInfoState orderInfoState : orderInfoStates){
            orderStateType = orderInfoState.getOrderState().getOrderStateType();
        }
        return orderStateType;
    }

    private List<OrderItemListResponse> getOrderItems(List<OrderItem> orderItems){
        List<OrderItemListResponse> itemListResponses = new ArrayList<>();
        for(OrderItem orderItem : orderItems){
            itemListResponses.add(
                    new OrderItemListResponse(
                            orderItem.getId(),
                            orderItem.getQuantity(),
                            getItemTitle(orderItem),
                            getItemId(orderItem)
                    )
            );
        }
        return itemListResponses;
    }

    private String getItemTitle(OrderItem orderItem){
        String itemTitle = null;
        if(orderItem.getOrderItemType().equals("PRODUCT")){
            itemTitle = ((ProductOrderItem) orderItem).getProduct().getTitle();
        }else if(orderItem.getOrderItemType().equals("SIDE")){
            itemTitle = ((SideProductOrderItem) orderItem).getSideProduct().getTitle();
        }else if(orderItem.getOrderItemType().equals("SELF")){
            itemTitle = ((SelfSaladOrderItem) orderItem).getSelfSalad().getTitle();
        }
        return itemTitle;
    }

    private Long getItemId(OrderItem orderItem){
        Long itemId = null;
        if(orderItem.getOrderItemType().equals("PRODUCT")){
            itemId = ((ProductOrderItem) orderItem).getProduct().getProductId();
        }else if(orderItem.getOrderItemType().equals("SIDE")){
            itemId = ((SideProductOrderItem) orderItem).getSideProduct().getSideProductId();
        }else if(orderItem.getOrderItemType().equals("SELF")){
            itemId = ((SelfSaladOrderItem) orderItem).getSelfSalad().getId();
        }
        return itemId;
    }
}
