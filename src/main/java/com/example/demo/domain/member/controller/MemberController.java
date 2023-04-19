package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.controller.form.MemberSignInForm;
import com.example.demo.domain.member.controller.form.MemberSignUpForm;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.request.MemberPasswordCheckRequest;
import com.example.demo.domain.member.service.request.MemberProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    final private MemberService memberService;

    @PostMapping("/check-email/{email}")
    public Boolean emailValidation(@PathVariable("email") String email) {
        log.info("emailValid(): " + email);

        return memberService.emailValidation(email);
    }

    @PostMapping("/check-nickname/{nickname}")
    public Boolean nicknameValidation(@PathVariable("nickname") String nickname) {
        log.info("nicknameValid(): " + nickname);

        return memberService.nicknameValidation(nickname);
    }

    @PostMapping("/check-admin/{adminCode}")
    public Boolean adminCodeValidation(@PathVariable("adminCode") String adminCode) {
        return memberService.adminCodeValidation(adminCode);
    }

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody MemberSignUpForm form) {
        log.info("signUp(): " + form);

        return memberService.signUp(form.toMemberSignUpRequest());
    }

    @PostMapping("/sign-in")
    public Map<String, String> signIn(@RequestBody MemberSignInForm form) {
        log.info("signIn(): " + form);

        return memberService.signIn(form.toMemberSignInRequest());
    }

    @DeleteMapping("/delete-member/{memberId}")
    public void deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }




    // 회원 프로필 정보 수정작업 저장
    @PutMapping("/profile-info/update/{userId}")
    public Boolean updateMemberInfo(@PathVariable("userId") Long memberId,
                                    @RequestBody MemberProfileRequest memberProfileRequest) {
        log.info("/member-profile/"+ memberId +", "+ memberProfileRequest);

        return memberService.updateMemberInfo(memberId, memberProfileRequest);
    }

    @PostMapping("/check-password")
    public Boolean passwordValidation(@RequestBody MemberPasswordCheckRequest memberRequest) {
        log.info("passwordValidation(): "+ memberRequest);

        return memberService.passwordValidation(memberRequest);
    }

}
