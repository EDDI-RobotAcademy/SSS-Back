package com.example.demo.domain.member.service;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.domain.member.service.request.MemberSignUpRequest;
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

        //authentication 가져오기 작업

        return true;
    }
}
