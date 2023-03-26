package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsImgRepository extends JpaRepository<ProductImg, Long> {
}
