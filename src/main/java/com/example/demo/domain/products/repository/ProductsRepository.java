package com.example.demo.domain.products.repository;

import com.example.demo.domain.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {


    Optional<List<Product>> findByProductIdIn(Set<Long> products);
}
