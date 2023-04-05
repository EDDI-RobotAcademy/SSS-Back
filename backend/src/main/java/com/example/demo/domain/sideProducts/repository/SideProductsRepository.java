package com.example.demo.domain.sideProducts.repository;

import com.example.demo.domain.sideProducts.entity.SideProduct;
import com.example.demo.domain.sideProducts.entity.SideProductImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import java.util.Optional;


public interface SideProductsRepository extends JpaRepository<SideProduct, Long> {


    SideProductImg findBySideProductId(Long sideProductId);
}
