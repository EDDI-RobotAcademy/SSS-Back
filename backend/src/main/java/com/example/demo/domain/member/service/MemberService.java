package com.example.demo.domain.member.service;

import com.example.demo.domain.member.service.request.MemberSignUpRequest;

public interface MemberService {
    Boolean emailValidation(String email);

    Boolean nicknameValidation(String nickname);

    Boolean signUp(MemberSignUpRequest memberSignUpRequest);

}
