package com.poc.ecommerce.reward.interfaces.eventhandlers;

import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Event Handler for the Order Cancelled Event that the Reward Bounded Context is interested in
 */
@Service
@EnableKafka
public class OrderCancelledEventHandler {

    @KafkaListener(topics = "")
    public void receive(OrderCancelledEvent event) {
    }
}
