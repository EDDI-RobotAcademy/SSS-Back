package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByMember_MemberIdAndDefaultCheck(Long memberId, char defaultCheck);
    Optional<List<Address>> findByMember_MemberIdAndDefaultCheckNot(Long memberId, char defaultCheck);

}
