package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

//    @Query("SELECT a from Address a JOIN FETCH a.memberProfile mp JOIN FETCH mp.member m where m.memberId = :memberId")
//    List<Address> findByMemberId(@Param("memberId") Long memberId);

    Optional<Address> findByMember_IdAndDefaultCheck(Long memberId, char defaultCheck);
    Optional<List<Address>> findByMember_IdAndDefaultCheckNot(Long memberId, char defaultCheck);
    Optional<List<Address>> findByMember_MemberId(Long memberId);
}
