package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Address;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a from Address a JOIN FETCH a.memberProfile mp JOIN FETCH mp.member m where m.memberId = :memberId")
    List<Address> findByMemberId(@Param("memberId") Long memberId);

    Address findFirstByMemberId(Long memberId);
}
