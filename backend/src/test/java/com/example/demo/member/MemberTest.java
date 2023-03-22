package com.example.demo.member;

import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입_확인() {
        assertTrue(memberService.signUp(new MemberSignUpRequest(
                "test2@sss.com", "test", "s5"
        )));
    }

    @Test
    public void 이메일_중복_확인() {
        assertTrue(memberService.emailValidation("testtest@sss.com"));
    }

    @Test
    public void 닉네임_중복_확인() {
        assertTrue(memberService.nicknameValidation("sss3"));
    }

    @Test
    public void 회원탈퇴_확인() {
        memberService.deleteMember(3L);
    }
}
