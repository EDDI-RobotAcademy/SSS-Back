package com.example.demo.domain.member.service.request;

import com.example.demo.domain.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;



@Getter
@ToString
@RequiredArgsConstructor
public class MemberSignUpRequest {

    final private String email;
    final private String password;
    final private String nickname;

    public Member toMember () {
        return new Member(email, nickname);
    }
}
