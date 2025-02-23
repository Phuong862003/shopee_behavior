package com.demo.shopee.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shopee.model.Products;
import com.demo.shopee.service.DataProcessorService;
import com.demo.shopee.service.RecommendationService;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final DataProcessorService dataProcessorService;

    public RecommendationController(RecommendationService recommendationService, DataProcessorService dataProcessorService) {
        this.recommendationService = recommendationService;
        this.dataProcessorService = dataProcessorService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getRecommendations(@PathVariable Long userId) {
        List<Products> recommendations = recommendationService.getRecommendations(userId);
        if (recommendations.isEmpty()) {
            recommendationService.trainModelAndSaveRecommendations(userId, dataProcessorService.getAllUserBehaviors());
            recommendations = recommendationService.getRecommendations(userId);
            if (recommendations.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.ok(recommendations);
    }
}
