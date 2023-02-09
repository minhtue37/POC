package com.poc.ecommerce.reward.interfaces.rest.dto;

import lombok.Data;

/**
 * Resource class for the Reward Send Command API
 */
@Data
public class RewardSendResource {
    private String userId;
    private String orderId;
    private String sku;
    private SKUType skuType;
}
