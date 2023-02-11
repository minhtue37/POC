package com.poc.ecommerce.reward.domain.model.commands;

import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import lombok.Data;

@Data
public class RewardCancelCommand {
    private String userId;
    private String orderId;

    public RewardCancelCommand(OrderCancelledEvent event) {
        this.userId = event.getUserId();
        this.orderId = event.getOrderId();
    }
}
