package com.demo.shopee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shopee.model.UserBehavior;
import com.demo.shopee.repository.UserBehaviorRepository;

@Service
public class DataProcessorService {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserBehaviorRepository userBehaviorRepository;

    public void processUserBehavior(UserBehavior userBehavior) {
        userBehaviorRepository.save(userBehavior);  // Save new behavior
        List<UserBehavior> allUserBehaviors = getAllUserBehaviors();
        recommendationService.trainModelAndSaveRecommendations(userBehavior.getUser().getId(), allUserBehaviors);
    }

    public List<UserBehavior> getAllUserBehaviors() {
        return userBehaviorRepository.findAll();  // Retrieve all user behaviors from the database
    }
}
