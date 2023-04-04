package com.example.demo.domain.member.controller.form;

import com.example.demo.domain.member.entity.AuthorityType;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberSignUpForm {

    private String email;
    private String password;
    private String nickname;
    private AuthorityType authorityName;
    private boolean adminCheck;
    private String adminCode;

    public MemberSignUpRequest toMemberSignUpRequest() {
        return new MemberSignUpRequest(email, password, nickname, authorityName, adminCheck);
    }
}
