package com.example.demo.domain.member.service;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.MemberProfile;
import com.example.demo.domain.member.service.request.*;

import java.util.List;
import java.util.Map;

public interface MemberService {

    Boolean emailValidation(String email);

    Boolean nicknameValidation(String nickname);

    Boolean adminCodeValidation(String adminCode);

    Boolean signUp(MemberSignUpRequest memberSignUpRequest);

    Map<String, String> signIn(MemberSignInRequest memberSignInRequest);

    void deleteMember(Long id);

    Boolean passwordValidation(MemberPasswordCheckRequest memberRequest);

    Boolean updateMemberInfo(Long memberId, MemberProfileRequest memberProfileRequest);

    MemberProfile getMemberProfile(Long memberId);

    Address getDefaultAddress(Long memberId);

    Boolean updateMemberAddress(Long memberId, AddressRequest reqAddress);

    Long registerAddress(Long memberId, AddressRequest reqAddress);

    List<Address> getOtherAddress(Long memberId);
}
