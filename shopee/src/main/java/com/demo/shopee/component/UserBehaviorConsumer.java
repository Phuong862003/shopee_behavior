package com.demo.shopee.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.demo.shopee.model.UserBehavior;
import com.demo.shopee.service.DataProcessorService;

@Component
public class UserBehaviorConsumer {
    private final DataProcessorService dataProcessorService;

    public UserBehaviorConsumer(DataProcessorService dataProcessorService) {
        this.dataProcessorService = dataProcessorService;
    }

    @KafkaListener(topics = "user-behavior", groupId = "recommendation-group")
    public void consumeUserBehavior(UserBehavior userBehavior) {
        dataProcessorService.processUserBehavior(userBehavior);
    }
}
