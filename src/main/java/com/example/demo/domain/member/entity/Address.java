package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String postCode;

    @Column(nullable = true)
    private String roadAddress;

    @Column(nullable = true)
    private String numberAddress;

    @Column(nullable = true)
    private String detailAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_update_id")
    private MemberUpdate memberUpdate;


    public Address(String postCode, String roadAddress, String numberAddress, String detailAddress) {
        this.postCode = postCode;
        this.roadAddress = roadAddress;
        this.numberAddress = numberAddress;
        this.detailAddress = detailAddress;
    }



    public static Address of(String postCode, String roadAddress, String numberAddress, String detailAddress) {
        return new Address(postCode, roadAddress, numberAddress, detailAddress);
    }

    public void setMemberUpdate(MemberUpdate memberUpdate) {
        this.memberUpdate = memberUpdate;
    }

}
