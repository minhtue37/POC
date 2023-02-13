package com.poc.ecommerce.reward.application.internal.outboundservices.acl;

import com.poc.ecommerce.reward.domain.model.commands.RewardSendCommand;
import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import com.poc.ecommerce.shareddomain.model.OrderDetail;
import com.poc.ecommerce.shareddomain.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

@Service
public class ExternalOrderDetailService {

    public OrderDetail getOrderDetail(RewardSendCommand rewardSendCommand) {
        // TODO: mock result of call external API to get order detail
        // currently, just return mock data
        return mockData(rewardSendCommand);
    }

    private OrderDetail mockData(RewardSendCommand rewardSendCommand) {
        String userId = rewardSendCommand.getUserId();
        String orderId = rewardSendCommand.getOrderId();
        return OrderDetail.builder().userId(userId).orderId(orderId).
                orderItems(Arrays.asList(OrderItem.builder().sku(mockSKU()).type(mockSKUType()).amount(mockAmount()).build())).
                build();
    }

    private String mockSKU() {
        return "XYZ12345";
    }

    private SKUType mockSKUType() {
        Random random = new Random();
        return random.nextBoolean() ? SKUType.NORMAL : SKUType.MISSION;
    }

    private long mockAmount() {
        return 1l;
    }
}
