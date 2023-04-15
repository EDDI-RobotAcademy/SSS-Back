package com.example.demo.domain.member.service;

import com.example.demo.domain.member.service.request.MemberPasswordCheckRequest;
import com.example.demo.domain.member.service.request.MemberSignInRequest;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import com.example.demo.domain.member.service.request.MemberProfileRequest;

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

}
