package com.poc.ecommerce.reward.interfaces.rest.dtos;

import lombok.Data;

/**
 * Resource class for the Reward Send Command API
 */
@Data
public class RewardSendRequest {
    private String userId;
    private String orderId;
}
