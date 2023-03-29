package com.example.demo.domain.member.controller.form;

import com.example.demo.domain.member.controller.DTO.request.MemberRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberRegisterForm {

    private String email;
    private String password;
    private String nickname;
    private String phoneNumber;
    private String postCode;
    private String roadAddress;
    private String numberAddress;
    private String detailAddress;

    public MemberRegisterRequest toMemberRegisterRequest () {
        return new MemberRegisterRequest(email, password, nickname, phoneNumber, postCode, roadAddress, numberAddress, detailAddress);
    }

}
