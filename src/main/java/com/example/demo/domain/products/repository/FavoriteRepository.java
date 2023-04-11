package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    @Query("select f from Favorite f join fetch f.product fp join fetch f.member fm where fp.productId = :productId and fm.memberId = :memberId")
    Optional<Favorite> findByProductAndMember(Long productId, Long memberId);

}