package com.example.demo.domain.sideProducts.repository;

import com.example.demo.domain.sideProducts.entity.SideProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SideProductsRepository extends JpaRepository<SideProduct, Long> {

    //상세페이지 볼때 ProductId 찾으려고
    Optional<SideProduct> findByProductId(Long productId);

    void deleteByProductId(Long productId);

}
