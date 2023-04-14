package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.SelfSalad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SelfSaladRepository extends JpaRepository<SelfSalad, Long> {
}
