package com.example.demo.domain.member.entity;


import com.example.demo.domain.member.service.request.MemberUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdate {


    @Id
    @Getter
    @Column(name = "member_update_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberUpdateId = null;

    @Getter
    @Column(nullable = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "memberUpdate", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    private Member member;

    public MemberUpdate(String phoneNumber, Set<Address> addresses, Member member) {
        this.phoneNumber = phoneNumber;
        if (addresses != null && !addresses.isEmpty()) {
            setAddresses(addresses);
        }
        this.member = member;
    }


    public MemberUpdate(Member member, MemberUpdateRequest memberUpdateRequest, Long memberUpdateId) {
        this.memberUpdateId = memberUpdateId;
        this.phoneNumber = memberUpdateRequest.getNewPhoneNumber();
        this.member = member;
    }

    public void addAddress(Address address) {
        address.setMemberUpdate(this);
        this.addresses.add(address);
    }


    public void setMember(Member member) {
        this.member = member;
    }

    private void setAddresses(Set<Address> addresses) {
        this.addresses = new HashSet<>();
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