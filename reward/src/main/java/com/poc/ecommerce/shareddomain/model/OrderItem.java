package com.poc.ecommerce.shareddomain.model;

import com.poc.ecommerce.reward.interfaces.rest.dtos.SKUType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderItem {
    private String sku;
    private SKUType type;
    private Long amount;
}
