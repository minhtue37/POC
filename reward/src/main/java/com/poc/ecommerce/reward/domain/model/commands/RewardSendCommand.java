package com.poc.ecommerce.reward.domain.model.commands;

import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RewardSendCommand {
    private String userId;
    private String orderId;
    private String sku;
    private SKUType skuType;

    public RewardSendCommand(RewardSendRequest request) {
        this.userId = request.getUserId();
        this.orderId = request.getOrderId();
        this.sku = request.getSku();
        this.skuType = request.getSkuType();
    }
}
