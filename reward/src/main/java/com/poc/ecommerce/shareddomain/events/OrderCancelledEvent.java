package com.poc.ecommerce.shareddomain.events;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderCancelledEvent {
    private String userId;
    private String orderId;

}
