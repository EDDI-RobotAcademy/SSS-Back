package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.MemberUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberUpdateRepository extends JpaRepository<MemberUpdate, Long> {
    Optional<MemberUpdate> findByMemberUpdateId(Long memberId);


}