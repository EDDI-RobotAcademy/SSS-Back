package com.example.demo.domain.selfSalad.repository;

import com.example.demo.domain.selfSalad.entity.SelfSalad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface SelfSaladRepository extends JpaRepository<SelfSalad, Long> {
    Optional<List<SelfSalad>> findByIdIn(Set<Long> selfSaladIds);
}
