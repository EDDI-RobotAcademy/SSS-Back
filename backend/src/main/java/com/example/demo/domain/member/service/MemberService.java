package com.example.demo.domain.member.service;

import com.example.demo.domain.member.service.request.MemberSignInRequest;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;

import java.util.Map;

public interface MemberService {

    Boolean emailValidation(String email);

    Boolean nicknameValidation(String nickname);

    Boolean signUp(MemberSignUpRequest memberSignUpRequest);

    Map<String, String> signIn(MemberSignInRequest memberSignInRequest);

    void deleteMember(Long id);
}
