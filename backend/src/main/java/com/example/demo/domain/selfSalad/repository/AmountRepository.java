package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepository extends JpaRepository<Amount, Long> {
}
