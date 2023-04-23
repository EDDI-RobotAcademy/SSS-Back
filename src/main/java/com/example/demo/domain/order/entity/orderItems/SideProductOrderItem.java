package com.example.demo.domain.order.entity.orderItems;

import com.example.demo.domain.order.entity.OrderInfo;
import com.example.demo.domain.sideProducts.entity.SideProduct;

import javax.persistence.*;

@Entity
@DiscriminatorValue("SIDE") // 하위클래스
public class SideProductOrderItem extends OrderItem{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "side_product_id", nullable = true)
    private SideProduct sideProduct;

    public SideProductOrderItem(Integer quantity, SideProduct sideProduct, OrderInfo orderInfo) {
        super(quantity, orderInfo); //OrderItem 의 생성자를 호출 + 해당 필드를 초기화
        this.quantity = quantity;
        this.sideProduct = sideProduct;
        this.orderInfo = orderInfo;
    }

    @Override
    public SideProduct getSideProduct(){
        return this.sideProduct;
    }

    @Override
    public String getOrderItemType() {
        return "SIDE";
    }
}
