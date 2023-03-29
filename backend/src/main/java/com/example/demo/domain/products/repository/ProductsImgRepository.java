package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.controller.form.ProductImgResponse;
import com.example.demo.domain.products.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductsImgRepository extends JpaRepository<ProductImg, Long> {
    @Query("select pi.imgId as imgId, pi.editedImg as editedImg from ProductImg pi join pi.product p where p.productId = :id")
    List<ProductImgResponse> findImagePathByProductId(Long id);
}
