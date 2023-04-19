package com.example.demo.domain.member.service;

import com.example.demo.domain.member.entity.*;
import com.example.demo.domain.member.repository.AddressRepository;
import com.example.demo.domain.member.repository.AdminCodeRepository;
import com.example.demo.domain.member.repository.MemberProfileRepository;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.service.request.*;
import com.example.demo.domain.security.entity.Authentication;
import com.example.demo.domain.security.entity.BasicAuthentication;
import com.example.demo.domain.security.repository.AuthenticationRepository;
import com.example.demo.domain.security.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;

    final private MemberProfileRepository memberProfileRepository;

    final private AdminCodeRepository adminCodeRepository;

    final private AuthenticationRepository authenticationRepository;

    final private AddressRepository addressRepository;

    final private RedisService redisService;

    //이메일 중복 확인
    @Override
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);

        if(maybeMember.isPresent()) {
            return false;
        }
        return true;
    }

    //닉네임 중복 확인
    @Override
    public Boolean nicknameValidation(String nickname) {
        Optional<Member> maybeNickName = memberRepository.findByNickName(nickname);

        if(maybeNickName.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean adminCodeValidation(String adminCode) {
        Optional<AdminCode> maybeAdmin = adminCodeRepository.findByCode(adminCode);
        if(maybeAdmin.isPresent()) {
            return true;
        }
        return false;
    }

    //회원가입
    @Override
    public Boolean signUp(MemberSignUpRequest memberSignUpRequest) {
        log.info("관리자코드: " + memberSignUpRequest.getAdminCode());

        if(memberSignUpRequest.getAdminCode() == null || memberSignUpRequest.getAdminCode().isEmpty()) {
            log.info("일반회원가입");
            final Member member = memberSignUpRequest.toMember();
            memberRepository.save(member);

            final BasicAuthentication authentication = new BasicAuthentication(
                    member,
                    Authentication.BASIC_AUTH,
                    memberSignUpRequest.getPassword()
            );
            authenticationRepository.save(authentication);

        } else {
            log.info("관리자가입");
            final Member member = memberSignUpRequest.toMember();
            memberRepository.save(member);

            final BasicAuthentication authentication = new BasicAuthentication(
                    member,
                    Authentication.BASIC_AUTH,
                    memberSignUpRequest.getPassword()
            );
            authenticationRepository.save(authentication);
        }

        return true;
    }

    //로그인
    @Override
    @Transactional
    public Map<String, String> signIn(MemberSignInRequest signInRequest) {
        String email = signInRequest.getEmail();
//        String authorityCode = signInRequest.getAuthorityCode();

        Optional<Member> maybeMember = memberRepository.findByEmail(email);

        if (maybeMember.isPresent()) {
            Member memberInfo = maybeMember.get();

            if (!memberInfo.isRightPassword(signInRequest.getPassword())) {
                throw new RuntimeException("비밀번호 잘못 입력");
            }

            UUID userToken = UUID.randomUUID();

            redisService.deleteByKey(userToken.toString());
            redisService.setKeyAndValue(userToken.toString(), memberInfo.getMemberId());

            Map<String, String> userInfo = new HashMap<>();

            userInfo.put("userToken", userToken.toString());
            userInfo.put("userEmail", memberInfo.getEmail());
            userInfo.put("userNickName", memberInfo.getNickname());
            userInfo.put("userId", memberInfo.getMemberId().toString());
            AuthorityType authorityType = memberInfo.getAuthority().getAuthorityName();
            userInfo.put("authorityType", authorityType.name());

            log.info("userProfile()" + userInfo);

            return userInfo;
        }

        throw new RuntimeException("가입 되어있지 않은 회원입니다. ");
    }

    //회원탈퇴
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    //정보변경




    // myPage 에서 회원 프로필 불러오기 (없으면 null)
    @Override
    public MemberProfile getMemberProfile(Long memberId){
        try {
            Member member = requireNonNull(checkMember(memberId));
            Optional<MemberProfile> maybeMemberProfile =
                    memberProfileRepository.findByMemberProfileId(member.getMemberId());

            return maybeMemberProfile.orElse(null);

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }
    // MemberProfile 등록 및 수정 요청
    @Override
    public Boolean updateMemberInfo(Long memberId, MemberProfileRequest reqMemberProfile){
        try {
            Member member = requireNonNull(checkMember(memberId));
            Optional<MemberProfile> maybeMemberProfile = memberProfileRepository.findByMemberProfileId(memberId);

            String reqPhoneNumber = reqMemberProfile.getPhoneNumber();
            String reqNickname= reqMemberProfile.getNickname();
            String reqPassword = reqMemberProfile.getNewPassword();

            MemberProfile myProfile = null;
            if(maybeMemberProfile.isPresent()){
                myProfile = maybeMemberProfile.get();

                if(reqPhoneNumber != null && !reqPhoneNumber.isEmpty()) {
                    myProfile.setPhoneNumber(reqPhoneNumber);
                }
                if(reqNickname != null && !reqNickname.isEmpty()) {
                    myProfile.setNickname(reqNickname);

                    member.setNickname(reqNickname);
                    memberRepository.save(member);

                }if(reqPassword != null && !reqPassword.isEmpty()){
                    updatePassword(member, reqPassword);
                }
                memberProfileRepository.save(myProfile);
                return true;
            }
            return false;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    // myPage 주소 변경 페이지 or 결제 페이지에서 기본주소 불러오기
    @Override
    public Address getDefaultAddress(Long memberId){
        try {
            Member member = requireNonNull(checkMember(memberId));

            Address defaultAddress =
                    addressRepository.findFirstByMemberId(member.getMemberId());
            if(defaultAddress != null){
                return defaultAddress;
            }
            return null;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    // 기본 주소 등록 or 수정
    @Override
    public Boolean updateMemberAddress(Long memberId, AddressRequest reqAddress){
        try {
            Member member = requireNonNull(checkMember(memberId));
            MemberProfile myProfile = requireNonNull(checkMemberProfile(member));
            Address defaultAddress;

            // 이미 기본 주소 존재 = 수정작업
            if(myProfile.getAddresses().get(0) != null){

                defaultAddress = myProfile.getAddresses().get(0);
                modifyDefaultAddress(defaultAddress, reqAddress);

                // 기본 주소 새로 등록
            }else{
                defaultAddress = reqAddress.toAddress(myProfile);
            }
            addressRepository.save(defaultAddress);
            return true;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    // 기본 주소 수정하기
    private Address modifyDefaultAddress(Address defaultAddress, AddressRequest reqAddress){
        // 기본 주소에서 변경된 사항 없음
        if( ! defaultAddress.getZipcode().equals(reqAddress.getZipcode())
                && ! defaultAddress.getAddressDetail().equals(reqAddress.getCity()))
        {
            defaultAddress.changeDefaultAddress(
                    reqAddress.getZipcode(),reqAddress.getCity(),
                    reqAddress.getStreet(), reqAddress.getAddressDetail());
            // address 저장 작업
            addressRepository.save(defaultAddress);
        }
        return defaultAddress;
    }








    // 결제창 : 신규 주소 등록 후 주소 id 반환
    @Override
    public Long registerAddress(Long memberId, AddressRequest reqAddress){
        try {
            Member member = requireNonNull(checkMember(memberId));
            MemberProfile myProfile = requireNonNull(checkMemberProfile(member));

            Address newAddress = reqAddress.toAddress(myProfile);
            addressRepository.save(newAddress);
            return newAddress.getId();

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

}
