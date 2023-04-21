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

    public Member checkMember(Long memberId){
        Optional<Member> maybeMember =
                memberRepository.findByMemberId(memberId);

        if(maybeMember.isPresent()) {
            return maybeMember.get();
        }
        throw new RuntimeException("회원을 찾을 수 없습니다: " + memberId);
    }

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


    // 회원 프로필 확인 절차 (없으면 생성)
    private MemberProfile checkMemberProfile(Member member){
        Optional<MemberProfile> myProfile =
                memberProfileRepository.findByMember_memberId(member.getMemberId());
        // 회원 프로필 존재
        if(myProfile.isPresent()){
            return myProfile.get();
        }
        // 회원 프로필 없음 = 생성
        MemberProfile newProfile = new MemberProfile(member);
        newProfile.setNickname(member.getNickname());
        memberProfileRepository.save(newProfile);
        return newProfile;
    }


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
            } else {
                myProfile = new MemberProfile(member);
            }


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

            Optional<Address> maybeDefaultAddress =
                    addressRepository.findByMember_MemberIdAndDefaultCheck(member.getMemberId(), 'Y');
            return maybeDefaultAddress.orElse(null);

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
            Optional<Address> maybeDefaultAddress =
                    addressRepository.findByMember_MemberIdAndDefaultCheck(member.getMemberId(), 'Y');
            Address defaultAddress;
            // 이미 기본 주소 존재 = 수정작업
            if(maybeDefaultAddress.isPresent()){
                defaultAddress = maybeDefaultAddress.get();
                modifyDefaultAddress(defaultAddress, reqAddress);
                // 기본 주소 새로 등록
            }else{
                defaultAddress = reqAddress.toAddress(member);
            }
            log.info("기본 주소 등록 및 수정 완료 : "+ defaultAddress.getZipcode(), defaultAddress.getDefaultCheck());
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
        }
        return defaultAddress;
    }

    // 비밀번호 수정
    private void updatePassword(Member member, String reqPassword) {

        Optional<Authentication> maybeAuthentication =
                authenticationRepository.findByMemberId(member.getMemberId());

        if (maybeAuthentication.isPresent() && maybeAuthentication.get() instanceof BasicAuthentication) {
            BasicAuthentication authentication = (BasicAuthentication) maybeAuthentication.get();
            String currentPassword = authentication.getPassword();

            // 패스워드 수정
            if (!reqPassword.equals(currentPassword)) {
                authenticationRepository.delete(authentication);
                authenticationRepository.flush();
                authentication = new BasicAuthentication(member, Authentication.BASIC_AUTH, reqPassword);
                authenticationRepository.save(authentication);
            }
        } else {
            BasicAuthentication authentication = new BasicAuthentication(member, Authentication.BASIC_AUTH, reqPassword);
            authenticationRepository.save(authentication);
        }
    }


    @Override
    @Transactional
    public Boolean passwordValidation(MemberPasswordCheckRequest memberRequest) {
        try {
            Member member = requireNonNull(checkMember(memberRequest.getMemberId()));

            if(member.isRightPassword(memberRequest.getPassword())) {
                return true;
            }
            return false;

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    // 결제창 : 신규 주소 등록 후 주소 id 반환
    @Override
    public Long registerAddress(Long memberId, AddressRequest reqAddress){
        try {
            Member member = requireNonNull(checkMember(memberId));

            Address newAddress = reqAddress.toAddress(member);
            addressRepository.save(newAddress);
            log.info("주소 등록 완료 : "+ newAddress.getZipcode());
            return newAddress.getId();

        } catch (RuntimeException ex) {
            log.info(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<Address> getOtherAddress(Long memberId){
        Optional<List<Address>> otherAddressList =
                addressRepository.findByMember_MemberIdAndDefaultCheckNot(memberId, 'Y');

        // 기본주소만 있다면 빈 리스트 반환
        return otherAddressList.isEmpty() ? Collections.emptyList() : otherAddressList.get();

    }

}
