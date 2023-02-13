package com.poc.ecommerce.shareddomain.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class OrderDetail {
    private String userId;
    private String orderId;
    private List<OrderItem> orderItems;
}
