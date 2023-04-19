package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {


    Address findFirstByMemberId(Long memberId);
}
