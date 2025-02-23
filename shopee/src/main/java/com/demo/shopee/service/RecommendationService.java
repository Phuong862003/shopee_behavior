package com.demo.shopee.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.shopee.model.Products;
import com.demo.shopee.model.Recommendation;
import com.demo.shopee.model.User;
import com.demo.shopee.model.UserBehavior;
import com.demo.shopee.repository.ProductRepository;
import com.demo.shopee.repository.RecommendationRepository;
import com.demo.shopee.repository.UserRepository;

@Service
public class RecommendationService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RecommendationRepository recommendationRepository;
    @Autowired
    UserRepository userRepository;

    public void trainModelAndSaveRecommendations(Long userId, List<UserBehavior> allUserBehaviors) {
        Map<Long, Set<Long>> userProductMap = new HashMap<>();
        for (UserBehavior behavior : allUserBehaviors) {
            userProductMap.computeIfAbsent(behavior.getUser().getId(), k -> new HashSet<>())
                          .add(behavior.getProducts().getId());
        }

        Set<Long> targetUserProducts = userProductMap.getOrDefault(userId, new HashSet<>());
        Map<Long, Double> similarityScores = new HashMap<>();

        for (Map.Entry<Long, Set<Long>> entry : userProductMap.entrySet()) {
            if (!entry.getKey().equals(userId)) {
                double similarity = calculateCosineSimilarity(targetUserProducts, entry.getValue());
                similarityScores.put(entry.getKey(), similarity);
            }
        }

        List<Map.Entry<Long, Double>> sortedSimilarUsers = new ArrayList<>(similarityScores.entrySet());
        sortedSimilarUsers.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        Set<Long> recommendedProducts = new HashSet<>();
        for (Map.Entry<Long, Double> similarUser : sortedSimilarUsers) {
            if (similarUser.getValue() > 0.0) {
                Set<Long> products = userProductMap.get(similarUser.getKey());
                for (Long productId : products) {
                    if (!targetUserProducts.contains(productId)) {
                        recommendedProducts.add(productId);
                    }
                }
            }
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        for (Long productId : recommendedProducts) {
            Products product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

            Recommendation recommendation = new Recommendation();
            recommendation.setUser(user);
            recommendation.setProducts(product);
            recommendation.setScore(Math.random());
            recommendationRepository.save(recommendation);
        }
    }

    private double calculateCosineSimilarity(Set<Long> setA, Set<Long> setB) {
        Set<Long> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);
        double numerator = intersection.size();
        double denominator = Math.sqrt(setA.size()) * Math.sqrt(setB.size());
        return denominator == 0 ? 0.0 : numerator / denominator;
    }

    public List<Products> getRecommendations(Long userId) {
        List<Recommendation> recs = recommendationRepository.findByUserIdOrderByScoreDesc(userId);
        List<Products> recommendedProducts = new ArrayList<>();
        for (Recommendation rec : recs) {
            productRepository.findById(rec.getProducts().getId()).ifPresent(recommendedProducts::add);
        }
        return recommendedProducts;
    }
}
