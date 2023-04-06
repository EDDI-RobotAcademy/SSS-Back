package com.example.demo.domain.member.repository;

import com.example.demo.domain.member.entity.AdminCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminCodeRepository extends JpaRepository<AdminCode, Long> {

    @Query("select ac from AdminCode ac where ac.code = :adminCode")
    Optional<AdminCode> findByCode(String adminCode);
}
