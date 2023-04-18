package com.example.demo.domain.sideProducts.repository;

import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;


public interface SideProductsRepository extends JpaRepository<SideProduct, Long> {


    SideProductImg findBySideProductId(Long sideProductId);

    Optional<List<SideProduct>> findByIdIn(Set<Long> sideProductIds);
}
