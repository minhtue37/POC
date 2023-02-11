package com.poc.ecommerce.shareddomain.events;

import lombok.Data;

@Data
public class OrderCancelledEvent {
    private String userId;
    private String orderId;

}
