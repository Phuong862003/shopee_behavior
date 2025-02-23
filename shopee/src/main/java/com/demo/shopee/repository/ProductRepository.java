package com.demo.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.shopee.model.Products;

public interface ProductRepository extends JpaRepository<Products, Long> {
    
}
