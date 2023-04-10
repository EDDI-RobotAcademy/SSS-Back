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
    private String city;

    @Column(nullable = true)
    private String street;

    @Column(nullable = true)
    private String addressDetail;

    @Column(nullable = true)
    private String zipcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_profile_id")
    private MemberProfile memberProfile;


    public Address(String city, String street, String addressDetail, String zipcode) {
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
        this.zipcode = zipcode;
    }



    public static Address of(String city, String street, String addressDetail, String zipcode) {
        return new Address(city, street, addressDetail, zipcode);
    }

    public void setMemberProfile(MemberProfile memberProfile) {
        this.memberProfile = memberProfile;
    }

}
