package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.Amount;
import com.example.demo.domain.selfSalad.entity.AmountType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmountRepository extends JpaRepository<Amount, Long> {
    Optional<Amount> findByAmountType(AmountType amountType);
}
