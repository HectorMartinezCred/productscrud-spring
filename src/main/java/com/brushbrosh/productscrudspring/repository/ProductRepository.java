package com.brushbrosh.productscrudspring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.brushbrosh.productscrudspring.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
}
