package com.example.demo.domain.member.service.request;

import com.example.demo.domain.member.entity.Authority;
import com.example.demo.domain.member.entity.AuthorityType;
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
    final private AuthorityType authorityName;
    private boolean adminCheck;
    private String adminCode;

    public MemberSignUpRequest(String email, String password, String nickname, AuthorityType authorityName, boolean adminCheck, String adminCode) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authorityName = authorityName;
        this.adminCheck = adminCheck;
        this.adminCode = adminCode;
    }

    public Member toMember () {
        return new Member(email, nickname, Authority.ofMember(authorityName), adminCheck);
    }
}
