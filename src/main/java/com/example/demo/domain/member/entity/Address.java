package com.example.demo.domain.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String zipcode;

    @Column(nullable = true)
    private String city;

    @Column(nullable = true)
    private String street;

    @Column(nullable = true)
    private String addressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = true)
    private char defaultCheck; //기본 주소 표시 'Y'


    public Address(String zipcode, String city, String street, String addressDetail) {
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
    }

    public Address(String zipcode, String city, String street, String addressDetail, Member member, char defaultCheck) {
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
        this.member = member;
        this.defaultCheck = defaultCheck;
    }
    

    public Address changeDefaultAddress(String zipcode, String city, String street, String addressDetail){
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
        this.addressDetail = addressDetail;
        return this;
    }



    public static Address of(String zipcode, String city, String street, String addressDetail) {
        return new Address(zipcode, city, street, addressDetail);
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
