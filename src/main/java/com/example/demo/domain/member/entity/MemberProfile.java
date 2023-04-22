package com.example.demo.domain.member.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberProfile {


    @Id
    @Column(name = "member_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProfileId = null;

    @Column(nullable = true)
    private String phoneNumber;

    @Setter
    @Column(nullable = false)
    private String nickname;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberProfile(Member member) {
        this.member = member;
    }


    public void setMember(Member member) {
        this.member = member;
    }


    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}