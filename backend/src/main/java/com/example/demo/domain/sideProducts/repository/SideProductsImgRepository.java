package com.example.demo.domain.sideProducts.repository;

import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

public interface SideProductsImgRepository extends JpaRepository<SideProductImg, Long> {


    @Modifying
    @Transactional
    @Query("delete from " +
            "SideProductImg spI " +
            "where spI.sideProduct.sideProductId = :sideProductId")
    void deleteSpecificProduct(@Param("sideProductId") Long sideProductId);

    @Query("select spI from " +
            "SideProductImg spI" +
            " join spI.sideProduct sp" +
            " where sp.sideProductId = :sideProductId")
    Optional<SideProduct> findImagePathBySideProductId(Long sideProductId);



}
