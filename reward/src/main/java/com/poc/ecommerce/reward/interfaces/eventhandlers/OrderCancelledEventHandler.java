package com.poc.ecommerce.reward.interfaces.eventhandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.ecommerce.reward.application.internal.commandservices.RewardCommandService;
import com.poc.ecommerce.reward.domain.model.commands.RewardCancelCommand;
import com.poc.ecommerce.shareddomain.events.OrderCancelledEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Event Handler for the Order Cancelled Event that the Reward Bounded Context is interested in
 */
@Component
@Log4j2
public class OrderCancelledEventHandler {

    private final RewardCommandService rewardCommandService;

    public OrderCancelledEventHandler(RewardCommandService rewardCommandService) {
        this.rewardCommandService = rewardCommandService;
    }

    @KafkaListener(topics = "${cancel-order.topic}")
    public void receive(String message) {
        log.info("msg: {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        OrderCancelledEvent event = null;
        try {
            event = objectMapper.readValue(message, OrderCancelledEvent.class);
        } catch (JsonProcessingException e) {
            log.error("can't convert to {}", OrderCancelledEvent.class);
            return;
        }
        this.rewardCommandService.cancel(new RewardCancelCommand(event));
    }

    @KafkaListener(topics = "${cloudkarafka.topic}")
    public void processMessage(String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
                               @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.debug("topics {}, partitions {}, offsets {}, msg: {}", topics.get(0), partitions.get(0), offsets.get(0), message);
    }
}
