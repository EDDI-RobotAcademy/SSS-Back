package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.orderItems.OrderItem;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long totalOrderPrice;

    @CreationTimestamp
    private Date orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Delivery delivery;

    @OneToOne(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Payment payment;

    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<OrderInfoState> orderInfoState = new HashSet<>(); // 주문 상태
    

    public OrderInfo(Long totalOrderPrice, Member member) {
        this.totalOrderPrice = totalOrderPrice;
        this.member = member;
    }
}
