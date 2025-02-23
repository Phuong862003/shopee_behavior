package com.demo.shopee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.shopee.model.UserBehavior;

public interface UserBehaviorRepository extends JpaRepository<UserBehavior, Long>{
    
}
