package com.example.demo.domain.member.service;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
import com.example.demo.domain.security.entity.Authentication;
import com.example.demo.domain.security.entity.BasicAuthentication;
import com.example.demo.domain.security.repository.AuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.member.entity.Member;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberRepository memberRepository;

    final private AuthenticationRepository authenticationRepository;

    //이메일 중복 확인
    public Boolean emailValidation(String email) {
        Optional<Member> maybeMember = memberRepository.findByEmail(email);

        if(maybeMember.isPresent()) {
            return false;
        }
        return true;
    }

    //닉네임 중복 확인
    public Boolean nicknameValidation(String nickname) {
        Optional<Member> maybeNickName = memberRepository.findByNickName(nickname);

        if(maybeNickName.isPresent()) {
            return false;
        }
        return true;
    }

    //회원가입
    public Boolean signUp(MemberSignUpRequest memberSignUpRequest) {
        final Member member = memberSignUpRequest.toMember();
        memberRepository.save(member);

        final BasicAuthentication authentication = new BasicAuthentication(
                member,
                Authentication.BASIC_AUTH,
                memberSignUpRequest.getPassword()
        );

        authenticationRepository.save(authentication);

        return true;
    }

    //회원탈퇴
    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}
