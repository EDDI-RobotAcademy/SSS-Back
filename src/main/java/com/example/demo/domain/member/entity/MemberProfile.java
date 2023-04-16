package com.example.demo.domain.member.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor

public class MemberProfile {


    @Id
    @Getter
    @Column(name = "member_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberProfileId = null;

    @Getter
    @Column(nullable = true)
    private String phoneNumber;

    @Getter
    @Setter
    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "memberProfile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberProfile(String phoneNumber, String nickname, Set<Address> addresses, Member member) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        if (addresses != null && !addresses.isEmpty()) {
            this.addresses = addresses;
        }
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
        this.addresses = new HashSet<>(addresses);
        for (Address address : addresses) {
            addAddress(address);
        }
    }
    public Set<Address> getAddresses() {
        return this.addresses;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}