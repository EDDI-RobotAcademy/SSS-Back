package com.example.demo.domain.member.controller.DTO.request;

import com.example.demo.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
@Getter
@ToString
@RequiredArgsConstructor
public class MemberRegisterRequest {

    final private String email;
    final private String password;
    final private String nickname;
    final private String phoneNumber;
    final private String postCode;
    final private String roadAddress;
    final private String numberAddress;
    final private String detailAddress;

    public Member toMember () {
        return  Member.of(email, nickname);
    }
}
