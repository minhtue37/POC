package com.poc.ecommerce.shareddomain.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderCancelledEvent {
    private String userId;
    private String orderId;

}
