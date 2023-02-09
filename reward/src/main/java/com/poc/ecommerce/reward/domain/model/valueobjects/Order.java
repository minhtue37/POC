package com.poc.ecommerce.reward.domain.model.valueobjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Order {

    @Column(name = "order_id")
    private String orderId;
}
