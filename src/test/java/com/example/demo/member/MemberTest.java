package com.example.demo.member;

import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.AuthorityType;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.request.MemberPasswordCheckRequest;
import com.example.demo.domain.member.service.request.MemberSignInRequest;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import com.example.demo.domain.member.service.request.MemberProfileRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class MemberTest {
    @Autowired
    private MemberService memberService;

    @Test
    public void 회원가입_확인() {
        assertTrue(memberService.signUp(new MemberSignUpRequest(
                "test@test.com", "test", "test", AuthorityType.MEMBER, false, null
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
        memberService.deleteMember(1L);
    }

    @Test
    public void 회원정보_변경_확인() {
        MemberProfileRequest memberProfileRequest = new MemberProfileRequest(
                "0x10-1234-5678", "aa",
                List.of(Address.of("우편번호", "주소", "지번", "상세주소")),"1234"
        );
        //변경할 전화번호 / 주소 (우편번호, 지역명, 지역주소, 상세주소)
        Long memberId = 1L; // 테스트할 회원 ID 선택
        assertTrue(memberService.updateMemberInfo(memberId, memberProfileRequest));
    }

    @Test
    public void 비밀번호_검증_확인() {
        MemberPasswordCheckRequest request = new MemberPasswordCheckRequest(1L, "1234");
        boolean result = memberService.passwordValidation(request);
        assertTrue(result);
    }

}