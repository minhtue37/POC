package com.poc.ecommerce.reward.domain.model.commands;

import com.poc.ecommerce.reward.interfaces.rest.dtos.RewardSendRequest;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import com.poc.ecommerce.shareddomain.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class RewardSendCommand {
    private String userId;
    private String orderId;

    private List<RewardSendDetail> rewardSendDetails;

    public RewardSendCommand(RewardSendRequest request) {
        this.userId = request.getUserId();
        this.orderId = request.getOrderId();
    }

    public void setRewardSendDetails(List<OrderItem> orderItems) {
        Map<SKUType, Long> reward = orderItems.stream().collect(Collectors.groupingBy(OrderItem::getType, Collectors.summingLong(OrderItem::getAmount)));
        this.rewardSendDetails = reward.entrySet().stream().map(e -> new RewardSendDetail(e.getKey(), e.getValue())).collect(Collectors.toList());
    }


    @AllArgsConstructor
    @Data
    public static class RewardSendDetail {
        private SKUType skuType;
        private Long amount;
    }
}

