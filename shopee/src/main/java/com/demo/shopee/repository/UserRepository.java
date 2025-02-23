package com.demo.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.shopee.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
