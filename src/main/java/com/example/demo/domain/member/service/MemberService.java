package com.example.demo.domain.member.service;

import com.example.demo.domain.member.entity.MemberProfile;

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
}
