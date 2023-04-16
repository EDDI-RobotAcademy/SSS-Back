package com.example.demo.domain.member.service.request;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.entity.MemberProfile;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberProfileRequest {

    private final String phoneNumber;
    private final String nickname;
    private final List<Address> addresses;
    private final String newPassword;

    public MemberProfile toMemberUpdate(Member member) {
        MemberProfile memberProfile = new MemberProfile(phoneNumber, nickname, new HashSet<>(), member);  // memberUpdate 객체 생성 시에는 기존에 등록된 주소는 사용하지 않음
        if (addresses != null && !addresses.isEmpty()) { // 새로운 주소 리스트가 있으면 추가
            for (Address address : addresses) {
                memberProfile.addAddress(address);
            }
        }
        return memberProfile;
    }
}