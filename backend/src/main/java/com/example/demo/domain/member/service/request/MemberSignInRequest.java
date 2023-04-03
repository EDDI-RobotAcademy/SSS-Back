package com.example.demo.domain.member.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MemberSignInRequest {

    final private String email;
    final private String password;
    final private String authorityCode;
}
