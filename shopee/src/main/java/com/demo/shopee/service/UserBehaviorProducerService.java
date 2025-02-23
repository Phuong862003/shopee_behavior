package com.demo.shopee.service;

import com.demo.shopee.model.UserBehavior;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserBehaviorProducerService {

    private final KafkaTemplate<String, UserBehavior> kafkaTemplate;

    public UserBehaviorProducerService(KafkaTemplate<String, UserBehavior> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserBehavior(String topic, UserBehavior userBehavior) {
        kafkaTemplate.send(topic, userBehavior);
    }
}
