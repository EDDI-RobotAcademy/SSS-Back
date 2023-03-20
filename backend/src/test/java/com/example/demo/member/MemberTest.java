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
                "test@sss.com", "test", "s3"
        )));
    }
}
