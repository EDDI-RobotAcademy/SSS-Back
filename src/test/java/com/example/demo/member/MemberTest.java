package com.example.demo.member;

import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.request.MemberSignInRequest;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입_확인() {
        assertTrue(memberService.signUp(new MemberSignUpRequest(
                "test@test.com", "test", "test"
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
    public void 로그인_확인() {
        MemberSignInRequest memberSignInRequest = new MemberSignInRequest("test@test.com", "test");
        Map<String, String> memberInfo = memberService.signIn(memberSignInRequest);
        String token = memberInfo.get("userToken");
        System.out.println(token);
    }

    @Test
    public void 회원탈퇴_확인() {
        memberService.deleteMember(3L);
    }
}
