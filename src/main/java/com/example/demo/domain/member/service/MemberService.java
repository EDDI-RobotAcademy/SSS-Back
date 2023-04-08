package com.example.demo.domain.member.service;

import com.example.demo.domain.member.service.request.MemberSignInRequest;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import com.example.demo.domain.member.service.request.MemberUpdateRequest;

import java.util.Map;

public interface MemberService {

    Boolean emailValidation(String email);

    Boolean nicknameValidation(String nickname);

    Boolean adminCodeValidation(String adminCode);

    Boolean signUp(MemberSignUpRequest memberSignUpRequest);

    Map<String, String> signIn(MemberSignInRequest memberSignInRequest);

    void deleteMember(Long id);

    Boolean updateMemberInfo(Long memberId, MemberUpdateRequest memberUpdateRequest);

}
