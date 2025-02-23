package com.demo.shopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.shopee.model.Recommendation;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{

    List<Recommendation> findByUserIdOrderByScoreDesc(Long userId);
    
}
