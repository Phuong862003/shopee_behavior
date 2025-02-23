package com.demo.shopee.service;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.shopee.dto.UserBehaviorResponseDTO;
import com.demo.shopee.model.Products;
import com.demo.shopee.model.User;
import com.demo.shopee.model.UserBehavior;
import com.demo.shopee.model.enums.Action;
import com.demo.shopee.repository.ProductRepository;
import com.demo.shopee.repository.UserBehaviorRepository;
import com.demo.shopee.repository.UserRepository;

@Service
public class UserBehaviorService {
    @Autowired
    private final UserBehaviorProducerService userBehaviorProducerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserBehaviorRepository userBehaviorRepository;

    public UserBehaviorService(UserBehaviorProducerService userBehaviorProducerService) {
        this.userBehaviorProducerService = userBehaviorProducerService;
    }

    public UserBehaviorResponseDTO createAndSendBehavior(Long userId, Long productId, String action) {
        // Validate và chuyển action thành enum
        Action actionEnum = validateAndConvertAction(action);

        // Kiểm tra user
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // Kiểm tra product
        Products product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        // Tạo UserBehavior
        UserBehavior userBehavior = new UserBehavior();
        userBehavior.setUser(user);
        userBehavior.setProducts(product);
        userBehavior.setAction(actionEnum);
        userBehavior.setTimestamp(new Timestamp(System.currentTimeMillis()));

        userBehaviorRepository.save(userBehavior);

        // Gửi sự kiện đến Kafka
        userBehaviorProducerService.sendUserBehavior(action, userBehavior);

        // Trả về DTO
        return new UserBehaviorResponseDTO(
            user.getId(),
            product.getId(),
            actionEnum.name(),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(userBehavior.getTimestamp().toInstant())
        );
    }

    

    private Action validateAndConvertAction(String action) {
        try {
            return Action.valueOf(action.toLowerCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid action: " + action);
        }
    }
    
}
