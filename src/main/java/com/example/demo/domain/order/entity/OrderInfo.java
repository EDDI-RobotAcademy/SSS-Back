package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.orderItems.ProductOrderItem;
import com.example.demo.domain.order.entity.orderItems.SelfSaladOrderItem;
import com.example.demo.domain.order.entity.orderItems.SideProductOrderItem;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class OrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductOrderItem> productItemList;

    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SideProductOrderItem> sidePoductOrderItemList;

    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SelfSaladOrderItem> selfSaladOrderItemList;

    @Column
    private Long totalOrderPrice;

    @CreationTimestamp
    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public OrderInfo(Long totalOrderPrice, Member member) {
        this.totalOrderPrice = totalOrderPrice;
        this.member = member;
    }
}
