package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Member.class)
    @JoinColumn(name="member_id")
    private Member member;

    @Column
    private String PhoneNumber;

    public PhoneNumber(String phoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }


    public static PhoneNumber of(String PhoneNumber) {
        return new PhoneNumber(PhoneNumber);
    }
}
