package com.demo.shopee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.shopee.dto.UserBehaviorResponseDTO;
import com.demo.shopee.model.UserBehavior;
import com.demo.shopee.service.UserBehaviorService;

@RestController
@RequestMapping("/user-behavior")
public class UserBehaviorController {
    private final UserBehaviorService userBehaviorService;

    public UserBehaviorController(UserBehaviorService userBehaviorService) {
        this.userBehaviorService = userBehaviorService;
    }

     @PostMapping("/{userId}/{productId}/{action}")
    public ResponseEntity<UserBehaviorResponseDTO> trackBehavior(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @PathVariable String action) {

        UserBehaviorResponseDTO response = userBehaviorService.createAndSendBehavior(userId, productId, action);
        return ResponseEntity.ok(response);
    }
}
