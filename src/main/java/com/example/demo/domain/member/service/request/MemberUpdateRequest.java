package com.example.demo.domain.member.service.request;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberUpdate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberUpdateRequest {

    private final String newPhoneNumber;
    private final Set<Address> newAddresses;
    private final String newPassword;

    public MemberUpdate toMemberUpdate(Member member) {
        MemberUpdate memberUpdate = new MemberUpdate(newPhoneNumber, null, member);  // memberUpdate 객체 생성 시에는 기존에 등록된 주소는 사용하지 않음
        if (newAddresses != null && !newAddresses.isEmpty()) { // 새로운 주소 리스트가 있으면 추가
            for (Address address : newAddresses) {
                memberUpdate.addAddress(address);
            }
        }
        return memberUpdate;
    }
}