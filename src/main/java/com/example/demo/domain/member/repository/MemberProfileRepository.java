package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {
    Optional<MemberProfile> findByMemberProfileId(Long memberId);


}