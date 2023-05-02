package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.controller.form.MemberSignInForm;
import com.example.demo.domain.member.controller.form.MemberSignUpForm;
import com.example.demo.domain.member.entity.Address;
import com.example.demo.domain.member.entity.MemberProfile;
import com.example.demo.domain.member.service.MemberService;
import com.example.demo.domain.member.service.request.AddressRequest;
import com.example.demo.domain.member.service.request.MemberPasswordCheckRequest;
import com.example.demo.domain.member.service.request.MemberProfileRequest;
import com.example.demo.domain.utility.TokenBasedController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController extends TokenBasedController {

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

    @DeleteMapping("/delete-member")
    public void deleteMember(HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        memberService.deleteMember(memberId);
    }


    // 회원 프로필 정보 불러오기
    @GetMapping("/profile-info")
    public MemberProfile getMemberProfile(HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        return memberService.getMemberProfile(memberId);
    }

    // 회원 프로필 정보 수정작업 저장
    @PutMapping("/profile-info/update")
    public Boolean updateMemberInfo(HttpServletRequest requestToken,
                                    @RequestBody MemberProfileRequest memberProfileRequest) {
        Long memberId = findMemberId(requestToken);
        log.info("/member-profile/"+ memberId +", "+ memberProfileRequest);

        return memberService.updateMemberInfo(memberId, memberProfileRequest);
    }

    // 회원 기본 주소 불러오기
    @GetMapping("/profile-address")
    public Address getDefaultAddress(HttpServletRequest requestToken) {
        log.info("getDefaultAddress()");
        Long memberId = findMemberId(requestToken);
        return memberService.getDefaultAddress(memberId);
    }

    // 기본 주소 등록 혹은 수정작업
    @PutMapping("/profile-address/update")
    public Boolean updateDefaultAddress(HttpServletRequest requestToken,
                                    @RequestBody AddressRequest reqAddress) {
        Long memberId = findMemberId(requestToken);
        log.info("/member-profile/"+ memberId +", "+ reqAddress);

        return memberService.updateMemberAddress(memberId, reqAddress);
    }

    // 신규 주소 등록
    @PutMapping("/profile-address/register")
    public Boolean registerMemberAddress(HttpServletRequest requestToken,
                                        @RequestBody AddressRequest reqAddress) {
        Long memberId = findMemberId(requestToken);
        log.info("/member-profile/"+ memberId +", "+ reqAddress);

        return memberService.registerMemberAddress(memberId, reqAddress);
    }

    // 결제창 : 기본 주소 외의 다른 주소들 반환
    @PutMapping("/profile-address/list")
    public List<Address> getAddressList(HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        log.info("/profile-address/list/"+ memberId);

        return memberService.getOtherAddress(memberId);
    }


    @PostMapping("/check-password")
    public Boolean passwordValidation(@RequestBody MemberPasswordCheckRequest memberRequest,
                                      HttpServletRequest requestToken) {
        Long memberId = findMemberId(requestToken);
        log.info("passwordValidation(): "+ memberRequest);

        return memberService.passwordValidation(memberId, memberRequest);
    }

}
