package com.example.demo.domain.order.entity;

import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.order.entity.items.SelfSaladItem;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@ToString(exclude = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class SelfSaladCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "selfSaladCart",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SelfSaladItem> selfSaladItemList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
