package com.demo.shopee.dto;

public class UserBehaviorResponseDTO {
    private Long userId;
    private Long productId;
    private String action;
    private String timestamp;

    public UserBehaviorResponseDTO(Long userId, Long productId, String action, String timestamp) {
        this.userId = userId;
        this.productId = productId;
        this.action = action;
        this.timestamp = timestamp;
    }

    // Getter và Setter
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
