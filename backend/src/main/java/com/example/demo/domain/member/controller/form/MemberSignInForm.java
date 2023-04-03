package com.example.demo.domain.member.controller.form;

import com.example.demo.domain.member.service.request.MemberSignInRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberSignInForm {

    private String email;
    private String password;
    private String authorityCode;

    public MemberSignInRequest toMemberSignInRequest(){
        return new MemberSignInRequest(email, password, authorityCode);
    }
}
