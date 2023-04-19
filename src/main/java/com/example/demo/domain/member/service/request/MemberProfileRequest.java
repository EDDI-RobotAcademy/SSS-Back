package com.example.demo.domain.member.service.request;

import com.example.demo.domain.member.entity.Address;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberProfileRequest {

    private final String phoneNumber;
    private final String nickname;
    private final Address address;
    private final String newPassword;

//    public MemberProfile toMemberUpdate(Member member) {
//        MemberProfile memberProfile = new MemberProfile(phoneNumber, nickname, new ArrayList<>(), member);  // memberUpdate 객체 생성 시에는 기존에 등록된 주소는 사용하지 않음
//        if (address != null ) { // 새로운 주소 리스트가 있으면 추가
//
//            if (phoneNumber != null && !phoneNumber.isEmpty()) { // 새로운 전화번호가 있으면 업데이트
//                memberProfile.setPhoneNumber(phoneNumber);
//            }
//        }
//            return memberProfile;
//        }

//    public MemberProfile toMemberUpdate(Member member) {
//        MemberProfile memberProfile = new MemberProfile(member);  // memberUpdate 객체 생성 시에는 기존에 등록된 주소는 사용하지 않음
//        if (address != null ) { // 새로운 주소가 있으면 추가
//            memberProfile.addAddress(address);
//
//            if (phoneNumber != null && !phoneNumber.isEmpty()) { // 새로운 전화번호가 있으면 업데이트
//                memberProfile.setPhoneNumber(phoneNumber);
//            }
//        }
//        return memberProfile;
//    }

}