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

    @OneToMany(mappedBy = "memberProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberProfile(String phoneNumber, String nickname, List<Address> addresses, Member member) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        if (addresses != null && !addresses.isEmpty()) {
            this.addresses = addresses;
        }
        this.member = member;
    }

    public MemberProfile(Member member) {
        this.member = member;
    }


    public void addAddress(Address address) {
        address.setMemberProfile(this);
        this.addresses.add(address);
    }


    public void setMember(Member member) {
        this.member = member;
    }

    private void setAddresses(List<Address> addresses) {
        this.addresses = new ArrayList<>(addresses);
        for (Address address : addresses) {
            addAddress(address);
        }
    }
    public List<Address> getAddresses() {
        return this.addresses;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}