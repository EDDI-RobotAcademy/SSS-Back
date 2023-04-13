package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.items.SideProductItem;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SideProductCart {
    /**
     * Order : 주문 테이블
     *
     * sideProductItem : 사이드 메뉴 (수량포함)
     * member : 로그인한 사용자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "sideProductCart",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SideProductItem> sideProductItemList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
