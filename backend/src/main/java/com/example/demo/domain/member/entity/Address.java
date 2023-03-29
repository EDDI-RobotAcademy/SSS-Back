package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(nullable = false)
    private String postCode;

    @Column(nullable = false)
    private String roadAddress;

    @Column(nullable = false)
    private String numberAddress;

    @Column(nullable = false)
    private String detailAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    private Address(Address address) {
        this.address = address;
    }
    public static Address (String postCode, String roadAddress, String numberAddress, String detailAddress) {
        return new Address(postCode, roadAddress, numberAddress, detailAddress);
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
